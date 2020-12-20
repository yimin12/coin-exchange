package com.yimin.feign;

import com.yimin.dto.MarketDto;
import com.yimin.dto.TradeMarketDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 20:33
 *   @Description :
 *
 */
@FeignClient(name = "exchange-service", configuration = OAuth2FeignConfig.class, path = "/markets")
public interface MarketServiceFeign {

    /**
     * 使用报价货币 以及 出售货币的ID
     */
    @GetMapping("/getMarket")
    MarketDto findByCoinId(@RequestParam("buyCoinId") Long buyCoinId, @RequestParam("sellCoinId") Long sellCoinId);

    @GetMapping("/getMarket/symbol")
    MarketDto findBySymbol(@RequestParam("symbol") String symbol);


    /**
     * 查询所有的交易市场
     *
     * @return
     */
    @GetMapping("/tradeMarkets")
    List<MarketDto> tradeMarkets();

    /**
     * 查询该交易对下的盘口数据
     *
     * @param symbol
     * @param value
     * @return
     */
    @GetMapping("/depthData/{symbol}/{type}")
    String depthData(@PathVariable("symbol") String symbol, @PathVariable("type") int value);

    /**
     * 使用市场的ids 查询该市场的交易趋势
     *
     * @param marketIds
     * @return
     */
    @GetMapping("/queryMarketsByIds")
    List<TradeMarketDto> queryMarkesByIds(@RequestParam("marketIds") String marketIds);

    /**
     * 通过交易对查询所有的交易数据
     *
     * @param symbol
     * @return
     */
    @GetMapping("/trades")
    String trades(@RequestParam("symbol") String symbol);


    /**
     * 刷新24小时成交数据
     *
     * @param symbol 交易对
     * @return
     */
    @GetMapping(value = "/refresh_24hour")
    void refresh24hour(@RequestParam("symbol") String symbol);
}
