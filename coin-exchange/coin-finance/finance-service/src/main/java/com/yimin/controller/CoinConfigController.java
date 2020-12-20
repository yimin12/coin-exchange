package com.yimin.controller;

import com.yimin.domain.CoinConfig;
import com.yimin.model.R;
import com.yimin.service.CoinConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 16:23
 *   @Description :
 *
 */
@RestController
@RequestMapping("/coinConfigs")
@Api(tags = "币种配置的控制器")
public class CoinConfigController {

    @Autowired
    private CoinConfigService coinConfigService;

    @GetMapping("/info/{coinId}")
    @ApiOperation(value = "查询币种的配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinId" ,value = "币种的id 值")
    })
    public R<CoinConfig> getCoinConfig(@PathVariable("coinId") Long coinId){
        CoinConfig coinConfig = coinConfigService.findByCoinId(coinId) ;
        return R.ok(coinConfig) ;
    }

    @PatchMapping
    @ApiOperation(value = "币种配置的修改操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinConfig" ,value ="coinConfig 的json 数据" )
    })
    public R update(@RequestBody @Validated CoinConfig coinConfig){
        boolean saveOrUpdate = coinConfigService.updateOrSave(coinConfig) ;
        if(saveOrUpdate){
            return R.ok() ;
        }
        return R.fail("修改失败") ;
    }
}
