package com.yimin.feign;

import com.yimin.dto.TradeAreaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 23:12
 *   @Description :
 *
 */
@FeignClient(name = "exchange-service",contextId = "tradingAreaServiceClient" ,configuration = OAuth2FeignConfig.class,path = "/tradeAreas")
public interface TradingAreaServiceClient {

    /**
     * 查询所有的交易区域
     * @return
     */
    @GetMapping("/query/All")
    List<TradeAreaDto> tradeAreas();
}
