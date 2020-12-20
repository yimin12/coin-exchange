package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CoinRecharge;
import com.yimin.model.R;
import com.yimin.service.CoinRechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 22:15
 *   @Description :
 *
 */
@RestController
@RequestMapping("/coinRecharges")
@Api(tags = "充币记录")
public class CoinRechargeController {

    @Autowired
    private CoinRechargeService coinRechargeService;

    @GetMapping("/records")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "coinId", value = "当前页"),
            @ApiImplicitParam(name = "userId", value = "用户的ID"),
            @ApiImplicitParam(name = "userName", value = "用户的名称"),
            @ApiImplicitParam(name = "mobile", value = "用户的手机号"),
            @ApiImplicitParam(name = "status", value = "充值的状态"),
            @ApiImplicitParam(name = "numMin", value = "充值金额的最小值"),
            @ApiImplicitParam(name = "numMax", value = "充值金额的最小值"),
            @ApiImplicitParam(name = "startTime", value = "充值开始时间"),
            @ApiImplicitParam(name = "endTime", value = "充值结束时间"),
    })
    public R<Page<CoinRecharge>> findByPage(
            @ApiIgnore Page<CoinRecharge> page, Long coinId,
            Long userId, String userName, String mobile,
            Byte status, String numMin, String numMax,
            String startTime, String endTime
    ) {
        Page<CoinRecharge> pageData = coinRechargeService.findByPage(page, coinId, userId, userName,
                mobile, status, numMin, numMax, startTime, endTime);
        return R.ok(pageData);
    }

    @GetMapping("/user/record")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current" ,value = "当前页") ,
            @ApiImplicitParam(name = "size" ,value = "显示的条数") ,
            @ApiImplicitParam(name = "coinId" ,value = "币种的Id") ,
    })
    @ApiOperation(value = "查询用户某种币的Id")
    public R<Page<CoinRecharge>> findUserCoinRecharge(@ApiIgnore Page<CoinRecharge> page, Long coinId){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<CoinRecharge> pageData = coinRechargeService.findUserCoinRecharge(page ,coinId, userId) ;
        return R.ok(pageData) ;
    }
}
