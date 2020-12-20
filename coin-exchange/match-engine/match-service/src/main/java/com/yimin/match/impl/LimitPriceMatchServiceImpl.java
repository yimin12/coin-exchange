package com.yimin.match.impl;

import com.yimin.enums.OrderDirection;
import com.yimin.match.MatchService;
import com.yimin.match.MatchServiceFactory;
import com.yimin.match.MatchStrategy;
import com.yimin.model.*;
import com.yimin.rocket.Source;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 20:53
 *   @Description :
 *
 */
@Service
@Slf4j
public class LimitPriceMatchServiceImpl implements MatchService, InitializingBean {

    @Autowired
    private Source source;


    /**
     * 进行订单的撮合交易
     *
     * @param order
     */
    @Override
    public void match(OrderBooks orderBooks, Order order) {
        log.info("开始撮合");
        if(order.isCancelOrder()){
            orderBooks.cancelOrder(order);
            Message<OrderDirection> message = MessageBuilder.withPayload(order.getOrderDirection()).build();
            source.cancelOrderOut().send(message);
            return; // 取消单操作
        }
        orderBooks.addOrder(order);
        // 1 进行数据的校验
        if (order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        // 2 获取一个挂单队列
        Iterator<Map.Entry<BigDecimal, MergeOrder>> markerQueueIterator = null;
        if (order.getOrderDirection() == OrderDirection.BUY) {
            markerQueueIterator = orderBooks.getCurrentLimitPriceIterator(OrderDirection.SELL);
        } else {
            markerQueueIterator = orderBooks.getCurrentLimitPriceIterator(OrderDirection.BUY);
        }
        // 是否退出循环
        boolean exitLoop = false;
        // 已经完成的订单
        List<Order> completedOrders = new ArrayList<>();
        // 产生的交易记录
        List<ExchangeTrade> exchangeTrades = new ArrayList<>();
        // 3 循环我们的队列
        while (markerQueueIterator.hasNext() && !exitLoop) {
            Map.Entry<BigDecimal, MergeOrder> markerOrderEntry = markerQueueIterator.next();
            BigDecimal markerPrice = markerOrderEntry.getKey();
            MergeOrder markerMergeOrder = markerOrderEntry.getValue();
            // 我花10 块钱买东西 ,别人的东西如果大于10 块 ,我就买不了
            if (order.getOrderDirection() == OrderDirection.BUY && order.getPrice().compareTo(markerPrice) < 0) {
                break;
            }
            // 我出售一个东西 10 ,结果有个人花5块钱
            if (order.getOrderDirection() == OrderDirection.SELL && order.getPrice().compareTo(markerPrice) > 0) {
                break;
            }
            Iterator<Order> markerIterator = markerMergeOrder.iterator();
            while (markerIterator.hasNext()) {
                Order marker = markerIterator.next();
                ExchangeTrade exchangeTrade = processMath(order, marker, orderBooks);
                exchangeTrades.add(exchangeTrade);
                if (order.isCompleted()) { // 经过一圈的吃单,我吃饱了
                    completedOrders.add(order);
                    exitLoop = true; // 退出最外层的循环
                    break;  // 退出当前的MergeOrder的循环
                }
                if (marker.isCompleted()) {// MergeOrder 的一个小的订单完成了
                    completedOrders.add(marker);
                    markerIterator.remove();
                }
            }
            if (markerMergeOrder.size() == 0) { // MergeOrder 已经吃完了
                markerQueueIterator.remove(); // 将该MergeOrder 从树上移除掉
            }
        }
        // 4 若我们的订单没有完成
        if (order.getAmount().compareTo(order.getTradedAmount()) > 0) {
            orderBooks.addOrder(order);
        }
        if (exchangeTrades.size() > 0) {
            // 5 发送交易记录
            handlerExchangeTrades(exchangeTrades);
        }
        if (completedOrders.size() > 0) {

            // 6 发送已经成交的交易记录
            completedOrders(completedOrders);
        }
    }
    /**
     * 进行委托单的匹配撮合交易
     *
     * @param taker  吃单
     * @param marker 挂单
     * @return ExchangeTrade 交易记录
     */
    private ExchangeTrade processMath(Order taker, Order marker, OrderBooks orderBooks) {
        // 1 定义交易的变量
        // 成交的价格
        BigDecimal dealPrice = marker.getPrice();
        // 成交的数量
        BigDecimal turnoverAmount = BigDecimal.ZERO;
        // 本次需要的数量
        BigDecimal needAmount = calcTradeAmount(taker); // 10  20
        // 本次提供给你的数量
        BigDecimal providerAmount = calcTradeAmount(marker); // 20 10


        turnoverAmount = needAmount.compareTo(providerAmount) <= 0 ? needAmount : providerAmount;

        if (turnoverAmount.compareTo(BigDecimal.ZERO) == 0) {
            return null; // 无法成交
        }

        // 设置本次吃单的成交数据
        taker.setTradedAmount(taker.getTradedAmount().add(turnoverAmount));
        BigDecimal turnoverTaker = turnoverAmount.multiply(dealPrice).setScale(orderBooks.getCoinScale(), RoundingMode.HALF_UP);
        taker.setTurnover(turnoverTaker);

        // 设置本次挂单的成交数据
        marker.setTradedAmount(marker.getTradedAmount().add(turnoverAmount));
        BigDecimal markerTurnover = turnoverAmount.multiply(dealPrice).setScale(orderBooks.getBaseCoinScale(), RoundingMode.HALF_UP);
        marker.setTurnover(markerTurnover);

        ExchangeTrade exchangeTrade = new ExchangeTrade();

        exchangeTrade.setAmount(turnoverAmount); // 设置购买的数量
        exchangeTrade.setPrice(dealPrice);  // 设置购买的价格
        exchangeTrade.setTime(System.currentTimeMillis()); // 设置成交的时间
        exchangeTrade.setSymbol(orderBooks.getSymbol());  // 设置成交的交易对
        exchangeTrade.setDirection(taker.getOrderDirection());  // 设置交易的方法
        exchangeTrade.setSellOrderId(marker.getOrderId()); // 设置出售方的id
        exchangeTrade.setBuyOrderId(taker.getOrderId()); // 设置买方的id

        exchangeTrade.setBuyTurnover(taker.getTurnover()); // 设置买方的交易额
        exchangeTrade.setSellTurnover(marker.getTurnover()); // 设置卖方的交易额

        /**
         * 处理盘口:
         *  我们的委托单肯定是: 将挂单的数据做了一部分消耗
         */
        if (marker.getOrderDirection() == OrderDirection.BUY) {
            // 减少挂单的数据量
            orderBooks.getBuyTradePlate().remove(marker, turnoverAmount);
        } else {
            orderBooks.getSellTradePlate().remove(marker, turnoverAmount);
        }

        return exchangeTrade;

    }

    /**
     * 计算本次的交易额
     *
     * @param order
     * @return
     */
    private BigDecimal calcTradeAmount(Order order) {
        return order.getAmount().subtract(order.getTradedAmount());
    }
    /**
     * 发送盘口数据,供以后我们前端的数据更新
     *
     * @param tradePlate
     */
    private void sendTradePlateData(TradePlate tradePlate) {
        Message<TradePlate> message = MessageBuilder
                .withPayload(tradePlate)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        source.plateOut().send(message);
    }

    /***
     * 订单的完成
     * @param completedOrders
     */
    private void completedOrders(List<Order> completedOrders) {
        Message<List<Order>> message = MessageBuilder
                .withPayload(completedOrders)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        source.completedOrdersOut().send(message);
    }

    /**
     * 处理订单的记录
     *
     * @param exchangeTrades
     */
    private void handlerExchangeTrades(List<ExchangeTrade> exchangeTrades) {
        Message<List<ExchangeTrade>> message = MessageBuilder
                .withPayload(exchangeTrades)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        source.exchangeTradesOut().send(message);
        log.info("本次成交的记录为:" + exchangeTrades);
        log.info("本次成交的记录为:" + exchangeTrades);

    }
    @Override
    public void afterPropertiesSet() throws Exception {
        MatchServiceFactory.addMatchService(MatchStrategy.LIMIT_PRICE,this);
    }

}
