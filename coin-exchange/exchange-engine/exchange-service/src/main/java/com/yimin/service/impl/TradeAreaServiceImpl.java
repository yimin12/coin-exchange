package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.istack.internal.NotNull;
import com.yimin.domain.Market;
import com.yimin.domain.UserFavoriteMarket;
import com.yimin.dto.CoinDto;
import com.yimin.feign.CoinServiceFeign;
import com.yimin.service.MarketService;
import com.yimin.service.UserFavoriteMarketService;
import com.yimin.vo.MergeDeptVo;
import com.yimin.vo.TradeAreaMarketVo;
import com.yimin.vo.TradeMarketVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.TradeArea;
import com.yimin.mapper.TradeAreaMapper;
import com.yimin.service.TradeAreaService;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 14:53
*   @Description : 
*
*/
    
@Service
public class TradeAreaServiceImpl extends ServiceImpl<TradeAreaMapper, TradeArea> implements TradeAreaService{

    @Autowired
    private MarketService marketService;

    @Autowired
    private CoinServiceFeign coinServiceFeign;

    @Autowired
    private UserFavoriteMarketService userFavoriteMarketService;

    /**
     * 分页查询交易区域
     *
     * @param page   分页参数
     * @param name   交易区域的名称
     * @param status 交易区域的状态
     * @return
     */
    @Override
    public Page<TradeArea> findByPage(Page<TradeArea> page, String name, Byte status) {
        return page(page, new LambdaQueryWrapper<TradeArea>()
                .eq(status != null, TradeArea::getStatus, status)
                .eq(!StringUtils.isEmpty(name), TradeArea::getName, name));
    }


    /**
     * 使用交易区域的状态查询列表
     *
     * @param status
     * @return
     */
    @Override
    public List<TradeArea> findAll(Byte status) {
        return list(new LambdaQueryWrapper<TradeArea>().eq(status != null, TradeArea::getStatus, status));
    }

    /**
     * 查询所有的交易区域以及市场
     *
     * @return
     */
    @Override
    public List<TradeAreaMarketVo> findTradeAreaMarket() {
        List<TradeArea> tradeAreas = list(new LambdaQueryWrapper<TradeArea>().eq(TradeArea::getStatus, 1).orderByAsc(TradeArea::getSort));
        if(CollectionUtils.isEmpty(tradeAreas)){
            return Collections.emptyList();
        }
        List<TradeAreaMarketVo> tradeAreaMarketVos = new ArrayList<TradeAreaMarketVo>();
        for(TradeArea tradeArea : tradeAreas){
            // 2 查询 我们 交易区域里面包含的市场
            List<Market> markets = marketService.getMarkersByTradeAreaId(tradeArea.getId());
            if (!CollectionUtils.isEmpty(markets)) {
                TradeAreaMarketVo tradeAreaMarketVo = new TradeAreaMarketVo();
                tradeAreaMarketVo.setAreaName(tradeArea.getName());
                tradeAreaMarketVo.setMarkets(markets2marketVos(markets));
                tradeAreaMarketVos.add(tradeAreaMarketVo);
            }
        }
        return tradeAreaMarketVos;
    }


    /**
     * 查询用户收藏的交易市场
     *
     * @param userId
     * @return
     */
    @Override
    public List<TradeAreaMarketVo> getUserFavoriteMarkets(Long userId) {
        List<UserFavoriteMarket> userFavoriteMarkets = userFavoriteMarketService.list(new LambdaQueryWrapper<UserFavoriteMarket>()
                .eq(UserFavoriteMarket::getUserId, userId));
        if(CollectionUtils.isEmpty(userFavoriteMarkets)){
            return Collections.emptyList() ;
        }
        List<Long> marketIds = userFavoriteMarkets.stream().map(UserFavoriteMarket::getMarketId).collect(Collectors.toList());
        // 创建一个TradeAreaMarketVo
        TradeAreaMarketVo tradeAreaMarketVo = new TradeAreaMarketVo();
        tradeAreaMarketVo.setAreaName("自选");
        List<Market> markets = marketService.listByIds(marketIds);
        List<TradeMarketVo> tradeMarketVos = markets2marketVos(markets);
        tradeAreaMarketVo.setMarkets(tradeMarketVos);
        return Arrays.asList(tradeAreaMarketVo) ;
    }

    /**
     * 将markets 转化为marketVos
     *
     * @param markets
     * @return
     */
    private List<TradeMarketVo> markets2marketVos(List<Market> markets) {
        return markets.stream().map(market -> toConvertVo(market)).collect(Collectors.toList());
    }

    /**
     * 将market 转化为TradeMarketVo
     *
     * @param market
     * @return
     */
    private TradeMarketVo toConvertVo(Market market) {
        TradeMarketVo tradeMarketVo = new TradeMarketVo();
        tradeMarketVo.setImage(market.getImg()); // 报价货币的图片
        tradeMarketVo.setName(market.getName());
        tradeMarketVo.setSymbol(market.getSymbol());
        //TODO
        // 价格的设置
        tradeMarketVo.setHigh(market.getOpenPrice()); // OpenPrice 开盘价格
        tradeMarketVo.setLow(market.getOpenPrice()); // 获取K线数据
        tradeMarketVo.setPrice(market.getOpenPrice()); // 获取K线数据
        tradeMarketVo.setCnyPrice(market.getOpenPrice()); // 计算获得
        tradeMarketVo.setCnyPrice(market.getOpenPrice()); // 计算活动
        // 获取报价货币
        @NotNull Long buyCoinId = market.getBuyCoinId();
        List<CoinDto> coins = coinServiceFeign.findCoins(Arrays.asList(buyCoinId));
        if (CollectionUtils.isEmpty(coins) || coins.size() > 1) {
            throw new IllegalArgumentException("报价货币错误");
        }
        CoinDto coinDto = coins.get(0);
        tradeMarketVo.setPriceUnit(coinDto.getName()); // 报价货币的名称
        // 交易的额度
        tradeMarketVo.setTradeMin(market.getTradeMin());
        tradeMarketVo.setTradeMax(market.getTradeMax());
        // 下单的数量限制
        tradeMarketVo.setNumMin(market.getNumMin());
        tradeMarketVo.setNumMax(market.getNumMax());
        // 手续费的设置
        tradeMarketVo.setSellFeeRate(market.getFeeSell());
        tradeMarketVo.setBuyFeeRate(market.getFeeBuy());
        // 价格的小数的位数
        tradeMarketVo.setNumScale(market.getNumScale());
        //  排序
        tradeMarketVo.setSort(market.getSort());
        // 设置交易量
        tradeMarketVo.setVolume(BigDecimal.ZERO); // // 日的总交易量
        tradeMarketVo.setAmount(BigDecimal.ZERO); // 日的总的交易额
        // 设置涨幅
        tradeMarketVo.setChange(0.00);
        // 设置合并的深度
        tradeMarketVo.setMergeDepth(getMergeDepths(market.getMergeDepth()));
        return tradeMarketVo;
    }

    /**
     * 获取合并的深度
     *
     * @param mergeDepth
     * @return
     */
    private List<MergeDeptVo> getMergeDepths(String mergeDepth) {
        String[] split = mergeDepth.split(",");
        if(split.length != 3){
            throw new IllegalArgumentException("合并深度不合法");
        }
        //  6(1/100000),5(100000),4 (10000)
        // 最小深度
        MergeDeptVo minMergeDeptVo = new MergeDeptVo();
        minMergeDeptVo.setMergeType("MIN");
        minMergeDeptVo.setValue(getDeptValue(Integer.valueOf(split[0])));

        MergeDeptVo defaultMergeDeptVo = new MergeDeptVo();
        defaultMergeDeptVo.setMergeType("DEFAULT"); //
        defaultMergeDeptVo.setValue(getDeptValue(Integer.valueOf(split[1])));

        MergeDeptVo maxMergeDeptVo = new MergeDeptVo();
        maxMergeDeptVo.setMergeType("MAX"); //
        maxMergeDeptVo.setValue(getDeptValue(Integer.valueOf(split[2])));

        List<MergeDeptVo> mergeDeptVos = new ArrayList<>();
        mergeDeptVos.add(minMergeDeptVo);
        mergeDeptVos.add(defaultMergeDeptVo);
        mergeDeptVos.add(maxMergeDeptVo);
        return mergeDeptVos;
    }

    //6  1/100000
    private BigDecimal getDeptValue(Integer scale) {
        BigDecimal bigDecimal = new BigDecimal(Math.pow(10, scale)); // Math.pow(10, scale) 指数操作
        return BigDecimal.ONE.divide(bigDecimal).setScale(scale, RoundingMode.HALF_UP); // 1/10^n
    }

}
