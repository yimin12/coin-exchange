package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.config.rocket.Source;
import com.yimin.domain.EntrustOrder;
import com.yimin.domain.ExchangeTrade;
import com.yimin.domain.Market;
import com.yimin.domain.TurnoverOrder;
import com.yimin.feign.AccountServiceFeign;
import com.yimin.mapper.EntrustOrderMapper;
import com.yimin.param.OrderParam;
import com.yimin.service.EntrustOrderService;
import com.yimin.service.MarketService;
import com.yimin.service.TurnoverOrderService;
import com.yimin.vo.TradeEntrustOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 14:53
*   @Description : 
*
*/
    
@Service
public class EntrustOrderServiceImpl extends ServiceImpl<EntrustOrderMapper, EntrustOrder> implements EntrustOrderService{

    @Autowired
    private MarketService marketService;

    @Autowired
    private TurnoverOrderService turnoverOrderService;

    @Autowired
    private AccountServiceFeign accountServiceFeign;

    @Autowired
    private Source source;

    /**
     * 分页查询委托单
     *
     * @param page   分页参数
     * @param userId 用户的id
     * @param symbol 交易对
     * @param type   交易类型
     * @return
     */
    @Override
    public Page<EntrustOrder> findByPage(Page<EntrustOrder> page, Long userId, String symbol, Integer type) {
        return page(page,
                new LambdaQueryWrapper<EntrustOrder>()
                        .eq(EntrustOrder::getUserId, userId)
                        .eq(!StringUtils.isEmpty(symbol), EntrustOrder::getSymbol, symbol)
                        .eq(type != null && type != 0, EntrustOrder::getType, type)
                        .orderByDesc(EntrustOrder::getCreated)

        );
    }


    /**
     * 获取用户的历史委托记录
     *
     * @param page   分页参数
     * @param symbol 交易对
     * @param userId 用户的Id
     * @return
     */
    @Override
    public Page<TradeEntrustOrderVo> getHistoryEntrustOrder(Page<EntrustOrder> page, String symbol, Long userId) {
        // 该用户对该交易对的交易记录
        Page<EntrustOrder> entrustOrderPage = page(page, new LambdaQueryWrapper<EntrustOrder>()
                .eq(EntrustOrder::getUserId, userId)
                .eq(EntrustOrder::getSymbol, symbol)
        );
        Page<TradeEntrustOrderVo> tradeEntrustOrderVoPage = new Page<>(page.getCurrent(), page.getSize());
        List<EntrustOrder> entrustOrders = entrustOrderPage.getRecords();
        if (CollectionUtils.isEmpty(entrustOrders)) {
            tradeEntrustOrderVoPage.setRecords(Collections.emptyList());
        } else {
            List<TradeEntrustOrderVo> tradeEntrustOrderVos = entrustOrders2tradeEntrustOrderVos(entrustOrders);
            tradeEntrustOrderVoPage.setRecords(tradeEntrustOrderVos);
        }

        return tradeEntrustOrderVoPage;
    }


    /**
     * 查询未完成的委托单
     *
     * @param page
     * @param symbol
     * @param userId
     * @return
     */
    @Override
    public Page<TradeEntrustOrderVo> getEntrustOrder(Page<EntrustOrder> page, String symbol, Long userId) {
        // 该用户对该交易对的交易记录
        Page<EntrustOrder> entrustOrderPage = page(page, new LambdaQueryWrapper<EntrustOrder>()
                .eq(EntrustOrder::getUserId, userId)
                .eq(EntrustOrder::getSymbol, symbol)
                .eq(EntrustOrder::getStatus, 0) // 查询未完成
        );
        Page<TradeEntrustOrderVo> tradeEntrustOrderVoPage = new Page<>(page.getCurrent(), page.getSize());
        List<EntrustOrder> entrustOrders = entrustOrderPage.getRecords();
        if (CollectionUtils.isEmpty(entrustOrders)) {
            tradeEntrustOrderVoPage.setRecords(Collections.emptyList());
        } else {
            List<TradeEntrustOrderVo> tradeEntrustOrderVos = entrustOrders2tradeEntrustOrderVos(entrustOrders);
            tradeEntrustOrderVoPage.setRecords(tradeEntrustOrderVos);
        }
        return tradeEntrustOrderVoPage;
    }

    /**
     * 将委托单装化为TradeEntrustOrderVo
     *
     * @param entrustOrders 委托单
     * @return TradeEntrustOrderVos
     */
    private List<TradeEntrustOrderVo> entrustOrders2tradeEntrustOrderVos(List<EntrustOrder> entrustOrders) {
        List<TradeEntrustOrderVo> tradeEntrustOrderVos = new ArrayList<>(entrustOrders.size());
        for (EntrustOrder entrustOrder : entrustOrders) {
            tradeEntrustOrderVos.add(entrustOrder2TradeEntrustOrderVo(entrustOrder));
        }
        return tradeEntrustOrderVos;
    }

    private TradeEntrustOrderVo entrustOrder2TradeEntrustOrderVo(EntrustOrder entrustOrder) {
        TradeEntrustOrderVo tradeEntrustOrderVo = new TradeEntrustOrderVo();
        tradeEntrustOrderVo.setOrderId(entrustOrder.getId());
        tradeEntrustOrderVo.setCreated(entrustOrder.getCreated());
        tradeEntrustOrderVo.setStatus(entrustOrder.getStatus().intValue());
        tradeEntrustOrderVo.setAmount(entrustOrder.getAmount());
        tradeEntrustOrderVo.setDealVolume(entrustOrder.getDeal());
        tradeEntrustOrderVo.setPrice(entrustOrder.getPrice());
        tradeEntrustOrderVo.setVolume(entrustOrder.getVolume());

        tradeEntrustOrderVo.setType(entrustOrder.getType().intValue()); //1-买入；2-卖出
        // 查询已经成交的额度
        BigDecimal dealAmount = BigDecimal.ZERO;
        BigDecimal dealVolume = BigDecimal.ZERO;
        if (tradeEntrustOrderVo.getType() == 1) {
            List<TurnoverOrder> buyTurnoverOrders = turnoverOrderService.getBuyTurnoverOrder(entrustOrder.getId(), entrustOrder.getUserId());
            if (!CollectionUtils.isEmpty(buyTurnoverOrders)) {
                for (TurnoverOrder buyTurnoverOrder : buyTurnoverOrders) {
                    BigDecimal amount = buyTurnoverOrder.getAmount();
                    dealAmount = dealAmount.add(amount);
                }
            }

        }
        if (tradeEntrustOrderVo.getType() == 2) {
            List<TurnoverOrder> sellTurnoverOrders = turnoverOrderService.getSellTurnoverOrder(entrustOrder.getId(), entrustOrder.getUserId());
            if (!CollectionUtils.isEmpty(sellTurnoverOrders)) {
                for (TurnoverOrder sellTurnoverOrder : sellTurnoverOrders) {
                    BigDecimal amount = sellTurnoverOrder.getAmount();
                    dealAmount = dealAmount.add(amount);
                }
            }
        }

        // 算买卖的额度
        tradeEntrustOrderVo.setDealAmount(dealAmount); // 已经成交的总额(钱)
        tradeEntrustOrderVo.setDealVolume(entrustOrder.getDeal()); // 成交的数量
        BigDecimal dealAvgPrice = BigDecimal.ZERO;
        if (dealAmount.compareTo(BigDecimal.ZERO) > 0) {
            dealAvgPrice = dealAmount.divide(entrustOrder.getDeal(), 8, RoundingMode.HALF_UP);
        }
        tradeEntrustOrderVo.setDealAvgPrice(dealAvgPrice); // 成交的评价价格
        return tradeEntrustOrderVo;
    }


    /**
     * 创建一个新的委托大
     *
     * @param userId     用户的id
     * @param orderParam 委托单的数据
     * @return
     */
    @Transactional
    @Override
    public Boolean createEntrustOrder(Long userId, OrderParam orderParam) {
        // 1 层层校验
        @NotBlank String symbol = orderParam.getSymbol();
        Market markerBySymbol = marketService.getMarkerBySymbol(symbol);
        if (markerBySymbol == null) {
            throw new IllegalArgumentException("您购买的交易对不存在");
        }

        BigDecimal price = orderParam.getPrice().setScale(markerBySymbol.getPriceScale(), RoundingMode.HALF_UP);
        BigDecimal volume = orderParam.getVolume().setScale(markerBySymbol.getNumScale(), RoundingMode.HALF_UP);

        // 计算成交额度
        BigDecimal mum = price.multiply(volume);

        // 交易数量的交易
        @NotNull BigDecimal numMax = markerBySymbol.getNumMax();
        @NotNull BigDecimal numMin = markerBySymbol.getNumMin();
        if (volume.compareTo(numMax) > 0 || volume.compareTo(numMin) < 0) {
            throw new IllegalArgumentException("交易的数量不在范围内");
        }

        // 校验交易额
        BigDecimal tradeMin = markerBySymbol.getTradeMin();
        BigDecimal tradeMax = markerBySymbol.getTradeMax();

        if (mum.compareTo(tradeMin) < 0 || mum.compareTo(tradeMax) > 0) {
            throw new IllegalArgumentException("交易的额度不在范围内");
        }
        // 计算手续费
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal feeRate = BigDecimal.ZERO;
        @NotNull Integer type = orderParam.getType();
        if (type == 1) { // 买入 buy
            feeRate = markerBySymbol.getFeeBuy();
            fee = mum.multiply(markerBySymbol.getFeeBuy());
        } else { // 卖出 sell
            feeRate = markerBySymbol.getFeeSell();
            fee = mum.multiply(markerBySymbol.getFeeSell());
        }
        EntrustOrder entrustOrder = new EntrustOrder();
        entrustOrder.setUserId(userId);
        entrustOrder.setAmount(mum);
        entrustOrder.setType(orderParam.getType().byteValue());
        entrustOrder.setPrice(price);
        entrustOrder.setVolume(volume);
        entrustOrder.setFee(fee);
        entrustOrder.setCreated(new Date());
        entrustOrder.setStatus((byte) 0);
        entrustOrder.setMarketId(markerBySymbol.getId());
        entrustOrder.setMarketName(markerBySymbol.getName());
        entrustOrder.setMarketType(markerBySymbol.getType());
        entrustOrder.setSymbol(markerBySymbol.getSymbol());
        entrustOrder.setFeeRate(feeRate);
        entrustOrder.setDeal(BigDecimal.ZERO);
        entrustOrder.setFreeze(entrustOrder.getAmount().add(entrustOrder.getFee())); // 冻结余额E

        boolean save = save(entrustOrder);
        if (save) {
            // 用户余额的扣减
            @NotNull Long coinId = null;
            if (type == 1) { // 购买操作
                coinId = markerBySymbol.getBuyCoinId();

            } else {
                coinId = markerBySymbol.getSellCoinId();
            }
            if (entrustOrder.getType() == (byte) 1) {
                accountServiceFeign.lockUserAmount(userId, coinId, entrustOrder.getFreeze(), "trade_create", entrustOrder.getId(), fee);
            }
            // 发送到撮合系统里面
            MessageBuilder<EntrustOrder> entrustOrderMessageBuilder = MessageBuilder.withPayload(entrustOrder).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON);

            source.outputMessage().send(entrustOrderMessageBuilder.build());
        }
        return save;
    }

    /**
     * 更新我们的委托单的数据
     *
     * @param exchangeTrade
     */
    @Override
    @Transactional
    public void doMatch(ExchangeTrade exchangeTrade) {
        String sellOrderId = exchangeTrade.getSellOrderId();
        String buyOrderId = exchangeTrade.getBuyOrderId();
        EntrustOrder sellOrder = getById(sellOrderId);
        EntrustOrder buyOrder = getById(buyOrderId);
        Long marketId = sellOrder.getMarketId();
        Market market = marketService.getById(marketId);


        // 1 新增成交记录
        addTurnOverOrderRecord(sellOrder, buyOrder, market, exchangeTrade);
        // 2 更新委托单
        updateEntrustOrder(sellOrder, buyOrder, exchangeTrade);
        // 3 余额的返还
        rollBackAccount(sellOrder, buyOrder, exchangeTrade, market);
    }


    /**
     * 添加成交记录
     *
     * @param
     */
    private void addTurnOverOrderRecord(EntrustOrder sellOrder, EntrustOrder buyOrder, Market market, ExchangeTrade exchangeTrade) {


        // 出售订单的成交记录
        TurnoverOrder sellTurnoverOrder = new TurnoverOrder();
        sellTurnoverOrder.setSellOrderId(sellOrder.getId());
        sellTurnoverOrder.setBuyCoinId(buyOrder.getId());
        sellTurnoverOrder.setBuyVolume(exchangeTrade.getAmount());
        sellTurnoverOrder.setAmount(exchangeTrade.getSellTurnover());

        sellTurnoverOrder.setBuyCoinId(market.getBuyCoinId());
        sellTurnoverOrder.setSellCoinId(market.getSellCoinId());
        sellTurnoverOrder.setCreated(new Date());
        sellTurnoverOrder.setBuyUserId(buyOrder.getUserId());
        sellTurnoverOrder.setSellUserId(sellOrder.getUserId());
        sellTurnoverOrder.setPrice(exchangeTrade.getPrice());
        sellTurnoverOrder.setBuyPrice(buyOrder.getPrice());
        sellTurnoverOrder.setTradeType(2);
        turnoverOrderService.save(sellTurnoverOrder);

        // 买方数据的成交记录
        TurnoverOrder buyTurnoverOrder = new TurnoverOrder();
        buyTurnoverOrder.setBuyOrderId(buyOrder.getId());
        buyTurnoverOrder.setSellOrderId(sellOrder.getId());
        buyTurnoverOrder.setAmount(exchangeTrade.getBuyTurnover());
        buyTurnoverOrder.setBuyVolume(exchangeTrade.getAmount());
        buyTurnoverOrder.setSellUserId(sellOrder.getUserId());
        buyTurnoverOrder.setBuyUserId(buyOrder.getUserId());
        buyTurnoverOrder.setSellCoinId(market.getSellCoinId());
        buyTurnoverOrder.setBuyCoinId(market.getBuyCoinId());
        buyTurnoverOrder.setCreated(new Date());
        sellTurnoverOrder.setTradeType(1);
        turnoverOrderService.save(sellTurnoverOrder);
    }

    /**
     * 更新委托单记录
     *
     * @param exchangeTrade
     */

    private void updateEntrustOrder(EntrustOrder sellOrder, EntrustOrder buyOrder, ExchangeTrade exchangeTrade) {

        /**
         * 已经成交的数量
         */
        sellOrder.setDeal(exchangeTrade.getAmount());
        buyOrder.setDeal(exchangeTrade.getAmount());
        BigDecimal volume = sellOrder.getVolume(); // 总的数量
        BigDecimal amount = exchangeTrade.getAmount(); // 本次成交的数量

        if (amount.compareTo(volume) == 0) { // 交易完成
            // 状态(已经完成)
            sellOrder.setStatus((byte) 1);
        }
        BigDecimal buyOrderVolume = buyOrder.getVolume();
        if (buyOrderVolume.compareTo(volume) == 0) { // 交易完成
            // 状态(已经完成)
            buyOrder.setStatus((byte) 1);
        }

        // 更新委托单
        updateById(sellOrder);
        updateById(buyOrder);
    }


    /**
     * 返回账户的余额
     *
     * @param exchangeTrade
     */
    private void rollBackAccount(EntrustOrder sellOrder, EntrustOrder buyOrder, ExchangeTrade exchangeTrade, Market market) {
        accountServiceFeign.transferBuyAmount(buyOrder.getUserId(),     // 买单用户ID
                sellOrder.getUserId(),                          // 卖单用户ID
                market.getBuyCoinId(),                           // 买单支付币种
                exchangeTrade.getBuyTurnover(),                      // 买单成交金额
                "币币交易",
                Long.valueOf(exchangeTrade.getBuyOrderId()));

        // 出售单需要
        accountServiceFeign.transferSellAmount(sellOrder.getUserId(),    // 卖单用户ID
                sellOrder.getUserId(),                           // 买单用户ID
                market.getSellCoinId(),                          // 卖单支付币种
                exchangeTrade.getSellTurnover(),                                      // 卖单成交数量
                "币币交易",                        // 业务类型：币币交易撮合成交
                Long.valueOf(exchangeTrade.getSellOrderId()));                         // 成交订单ID
    }

    @Override
    public void cancleEntrustOrder(Long orderId) {
        // 取消委托单
        // 1 将该委托单从撮合引擎里面的委托单账本里面移除
        EntrustOrder entrustOrder = new EntrustOrder();
        entrustOrder.setStatus((byte) 2);
        entrustOrder.setId(orderId);
        Message<EntrustOrder> message = MessageBuilder.withPayload(entrustOrder).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();
        source.outputMessage().send(message);

    }

    /**
     * 数据库里面委托单的取消
     *
     * @param orderId
     */
    @Override
    public void cancleEntrustOrderToDb(String orderId) {
        // 2 数据库的操作
        if (StringUtils.hasText(orderId)) {
            Long orderIdVal = Long.valueOf(orderId);
            EntrustOrder entrustOrder = getById(orderId);
            entrustOrder.setStatus((byte) 2);
            updateById(entrustOrder);
        }
    }
}
