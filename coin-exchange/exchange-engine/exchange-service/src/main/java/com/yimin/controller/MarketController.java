package com.yimin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.constant.Constants;
import com.yimin.domain.DepthItemVo;
import com.yimin.domain.Market;
import com.yimin.domain.TurnoverOrder;
import com.yimin.dto.MarketDto;
import com.yimin.dto.TradeMarketDto;
import com.yimin.feign.MarketServiceFeign;
import com.yimin.feign.OrderBooksFeignClient;
import com.yimin.mappers.MarketDtoMappers;
import com.yimin.model.R;
import com.yimin.service.MarketService;
import com.yimin.service.TurnoverOrderService;
import com.yimin.vo.DepthsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 21:16
 *   @Description :
 *
 */
@RestController
@RequestMapping("/markets")
@Api(tags = "交易市场的控制器")
public class MarketController implements MarketServiceFeign {

    @Autowired
    private MarketService marketService;

    @Autowired
    private TurnoverOrderService turnoverOrderService;

    @Autowired
    private OrderBooksFeignClient orderBooksFeignClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping
    @ApiOperation(value = "交易市场的分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数")
    })
    @PreAuthorize("hasAuthority('trade_market_query')")
    public R<Page<Market>> findByPage(@ApiIgnore Page<Market> page, Long tradeAreaId, Byte status) {
        Page<Market> pageData = marketService.findByPage(page, tradeAreaId, status);
        return R.ok(pageData);
    }


    @PostMapping("/setStatus")
    @ApiOperation(value = "启用/禁用交易市场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "market的json数据")
    })
    @PreAuthorize("hasAuthority('trade_market_update')")
    public R setStatus(@RequestBody Market market) {
        boolean updateById = marketService.updateById(market);
        if (updateById) {
            return R.ok();
        }
        return R.fail("状态设置失败");
    }


    @PostMapping
    @ApiOperation(value = "新增一个市场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "marketjson")
    })
    @PreAuthorize("hasAuthority('trade_market_create')")
    public R save(@RequestBody Market market) {
        boolean save = marketService.save(market);
        if (save) {
            return R.ok();
        }
        return R.fail("新增失败");
    }


    @PatchMapping
    @ApiOperation(value = "修改一个市场")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "market", value = "marketjson")
    })
    @PreAuthorize("hasAuthority('trade_market_update')")
    public R update(@RequestBody Market market) {
        boolean updateById = marketService.updateById(market);
        if (updateById) {
            return R.ok();
        }
        return R.fail("修改失败");
    }


    @GetMapping("/all")
    @ApiOperation(value = "查询所有的交易市场")
    public R<List<Market>> listMarks() {
        return R.ok(marketService.list());
    }


    @ApiOperation(value = "通过的交易对以及深度查询当前的市场的深度数据")
    @GetMapping("/depth/{symbol}/{dept}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "symbol", value = "交易对"),
            @ApiImplicitParam(name = "dept", value = "深度类型"),
    })
    public R<DepthsVo> findDeptVosSymbol(@PathVariable("symbol") String symbol, String dept) {
        // 交易市场
        Market market = marketService.getMarkerBySymbol(symbol);

        DepthsVo depthsVo = new DepthsVo();
        depthsVo.setCnyPrice(market.getOpenPrice()); // CNY的价格
        depthsVo.setPrice(market.getOpenPrice()); // GCN的价格
        Map<String, List<DepthItemVo>> depthMap = orderBooksFeignClient.querySymbolDepth(symbol);
        if (!CollectionUtils.isEmpty(depthMap)) {
            depthsVo.setAsks(depthMap.get("asks"));
            depthsVo.setBids(depthMap.get("bids"));
        }
        return R.ok(depthsVo);

    }

    @ApiOperation(value = "查询成交记录")
    @GetMapping("/trades/{symbol}")
    public R<List<TurnoverOrder>> findSymbolTurnoverOrder(@PathVariable("symbol") String symbol) {
        List<TurnoverOrder> turnoverOrders = turnoverOrderService.findBySymbol(symbol);
        return R.ok(turnoverOrders);
    }


    /**
     * K 线的查询
     *
     * @param symbol 交易对
     * @param type   K 线类型
     * @return
     */
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "symbol" ,value = "交易对"),
                    @ApiImplicitParam(name = "type" ,value = "k线类型")}
    )
    @GetMapping("/kline/{symbol}/{type}")
    public R<List<JSONArray>> queryKLine(@PathVariable("symbol") String symbol, @PathVariable("type") String type) {
        // 我们的K 线放在Redis 里面
        String redisKey = new StringBuilder(Constants.REDIS_KEY_TRADE_KLINE).append(symbol.toLowerCase()).append(":").append(type).toString();
        List<String> klines = redisTemplate.opsForList().range(redisKey, 0, Constants.REDIS_MAX_CACHE_KLINE_SIZE - 1);
        List<JSONArray> result =  new ArrayList<>(klines.size()) ;

        if (!CollectionUtils.isEmpty(klines)) {
            for (String kline : klines) {
                // 先把字符串转化为json的数组
                JSONArray objects = JSON.parseArray(kline);
                // 这样前端获取到的就是数字类型
                result.add(objects) ;
            }
            return R.ok(result);
        }

        return null;
    }

    /**
     * 使用报价货币 以及 出售的货币的iD
     *
     * @param buyCoinId
     * @param sellCoinId
     * @return
     */
    @Override
    public MarketDto findByCoinId(Long buyCoinId, Long sellCoinId) {
        MarketDto marketDto = marketService.findByCoinId(buyCoinId, sellCoinId);
        return marketDto;
    }

    @Override
    public MarketDto findBySymbol(String symbol) {
        Market markerBySymbol = marketService.getMarkerBySymbol(symbol);
        return MarketDtoMappers.INSTANCE.toConvertDto(markerBySymbol);
    }


    /**
     * 查询所有的交易市场
     *
     * @return
     */
    @Override
    public List<MarketDto> tradeMarkets() {
        return marketService.queryAllMarkets();
    }

    /**
     * 查询该交易对下的盘口数据
     *
     * @param symbol
     * @param value
     * @return
     */
    @Override
    public String depthData(String symbol, int value) {
        R<DepthsVo> deptVosSymbol = findDeptVosSymbol(symbol, value + "");
        DepthsVo data = deptVosSymbol.getData();
        return JSON.toJSONString(data);
    }

    /**
     * 使用市场的ids 查询该市场的交易趋势
     *
     * @param marketIds
     * @return
     */
    @Override
    public List<TradeMarketDto> queryMarkesByIds(String marketIds) {
        return null;
    }

    /**
     * 通过交易对查询所有的交易数据
     *
     * @param symbol
     * @return
     */
    @Override
    public String trades(String symbol) {
        return null;
    }

    /**
     * 刷新24小时成交数据
     *
     * @param symbol 交易对
     * @return
     */
    @Override
    public void refresh24hour(String symbol) {

    }
}
