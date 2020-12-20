package com.yimin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.dto.CoinDto;
import com.yimin.dto.MarketDto;
import com.yimin.feign.CoinServiceFeign;
import com.yimin.mappers.MarketDtoMappers;
import com.yimin.service.ConfigService;
import com.yimin.service.TurnoverOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.MarketMapper;
import com.yimin.domain.Market;
import com.yimin.service.MarketService;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 14:53
*   @Description : 
*
*/
    
@Service
@Slf4j
public class MarketServiceImpl extends ServiceImpl<MarketMapper, Market> implements MarketService{

    @Autowired
    private CoinServiceFeign coinServiceFeign;

    @Autowired
    private TurnoverOrderService turnoverOrderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ConfigService configService;

    /**
     * 分页查询市场的配置
     *
     * @param page        分页参数
     * @param tradeAreaId 交易区域的ID
     * @param status      状态
     * @return
     */
    @Override
    public Page<Market> findByPage(Page<Market> page, Long tradeAreaId, Byte status) {
        return page(page,
                new LambdaQueryWrapper<Market>()
                        .eq(tradeAreaId != null, Market::getTradeAreaId, tradeAreaId)
                        .eq(status != null, Market::getStatus, status)
        );
    }


    /**
     * 新增我们货币的信息
     *
     * @param entity
     * @return
     */
    @Override
    public boolean save(Market entity) {
        log.info("开始新增市场数据{}", JSON.toJSONString(entity));
        @NotBlank Long sellCoinId = entity.getSellCoinId(); // /报价货币
        @NotNull Long buyCoinId = entity.getBuyCoinId(); // 基础货币
        List<CoinDto> coins = coinServiceFeign.findCoins(Arrays.asList(sellCoinId, buyCoinId));
        if (CollectionUtils.isEmpty(coins) || coins.size() != 2) {
            throw new IllegalArgumentException("货币输入错误");
        }
        CoinDto coinDto = coins.get(0);
        CoinDto sellCoin = null;
        CoinDto buyCoin = null;
        if (coinDto.getId().equals(sellCoinId)) {
            sellCoin = coinDto;
            buyCoin = coins.get(1);
        } else {
            sellCoin = coins.get(1);
            buyCoin = coinDto;
        }

        entity.setName(sellCoin.getName() + "/" + buyCoin.getName()); // 交易市场的名称  报价货币/基础货币
        entity.setTitle(sellCoin.getTitle() + "/" + buyCoin.getTitle()); // 交易市场的标题 报价货币/基础货币
        entity.setSymbol(sellCoin.getName() + buyCoin.getName()); // 交易市场的标识 报价货币基础货币
        entity.setImg(sellCoin.getImg()); // 交易市场的图标

        return super.save(entity);
    }


    /**
     * 使用交易区域的id 查询该区域下的市场
     *
     * @param id 区域的Id
     * @return
     */
    @Override
    public List<Market> getMarkersByTradeAreaId(Long id) {
        return list(new LambdaQueryWrapper<Market>()
                .eq(Market::getTradeAreaId, id)
                .eq(Market::getStatus, 1)
                .orderByAsc(Market::getSort)
        );
    }

    /**
     * 使用交易对查询市场
     *
     * @param symbol
     * @return
     */
    @Override
    public Market getMarkerBySymbol(String symbol) {
        return getOne(new LambdaQueryWrapper<Market>().eq(Market::getSymbol, symbol));
    }

    /**
     * 使用报价货币和基础货币查询市场
     *
     * @param buyCoinId
     * @param sellCoinId
     * @return
     */
    @Override
    public MarketDto findByCoinId(Long buyCoinId, Long sellCoinId) {
        LambdaQueryWrapper<Market> eq = new LambdaQueryWrapper<Market>()
                .eq(Market::getBuyCoinId, buyCoinId)
                .eq(Market::getSellCoinId, sellCoinId)
                .eq(Market::getStatus, 1);
        Market one = getOne(eq);
        if (one == null) {
            return null;
        }
        MarketDto marketDto = MarketDtoMappers.INSTANCE.toConvertDto(one);
        return marketDto;
    }

    /**
     * 查询所有的市场数据
     *
     * @return
     */
    @Override
    public List<MarketDto> queryAllMarkets() {
        List<Market> list = list(new LambdaQueryWrapper<Market>().eq(Market::getStatus, 1));
        return MarketDtoMappers.INSTANCE.toConvertDto(list);
    }

    @Override
    public List<Market> queryByAreaId(Long id) {
        return null;
    }
}
