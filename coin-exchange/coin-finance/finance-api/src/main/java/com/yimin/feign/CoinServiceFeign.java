package com.yimin.feign;

import com.yimin.dto.CoinDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 16:45
 *   @Description :
 *
 */

@FeignClient(name = "finance-service",configuration = OAuth2FeignConfig.class,path = "/coins")
public interface CoinServiceFeign {

    @GetMapping("/list")
    public List<CoinDto> findCoins(@RequestParam(name = "coinIds") List<Long> coinIds) ;
}
