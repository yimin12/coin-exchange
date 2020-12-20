package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CashWithdrawals;
import com.yimin.model.CashSellParam;
import com.yimin.model.R;
import com.yimin.service.CashWithdrawalsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 21:48
 *   @Description :
 *
 */
@RestController
@RequestMapping("/cashWithdrawals")
@Api(tags = "GCN提现记录")
public class CashWithdrawalsController {

    @Autowired
    private CashWithdrawalsService cashWithdrawalsService;


    @GetMapping("/records")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "userId", value = "用户的ID"),
            @ApiImplicitParam(name = "userName", value = "用户的名称"),
            @ApiImplicitParam(name = "mobile", value = "用户的手机号"),
            @ApiImplicitParam(name = "status", value = "充值的状态"),
            @ApiImplicitParam(name = "numMin", value = "充值金额的最小值"),
            @ApiImplicitParam(name = "numMax", value = "充值金额的最小值"),
            @ApiImplicitParam(name = "startTime", value = "充值开始时间"),
            @ApiImplicitParam(name = "endTime", value = "充值结束时间"),
    })
    public R<Page<CashWithdrawals>> findByPage(
            @ApiIgnore Page<CashWithdrawals> page,
            Long userId, String userName, String mobile,
            Byte status, String numMin, String numMax,
            String startTime, String endTime
    ){
        Page<CashWithdrawals> pageData = cashWithdrawalsService.findByPage(page, userId, userName, mobile, status, numMin, numMax, startTime, endTime);
        return R.ok(pageData) ;
    }

    @GetMapping("/user/records")
    @ApiOperation(value = "查询当前用户的充值记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页显示的大小") ,
            @ApiImplicitParam(name = "status",value = "充值的状态") ,
    })
    public R<Page<CashWithdrawals>> findUserCashRecharge(@ApiIgnore Page<CashWithdrawals> page , Byte status){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<CashWithdrawals> cashWithdrawalsPage = cashWithdrawalsService.findCashWithdrawals(page ,userId,status) ;
        return R.ok(cashWithdrawalsPage) ;
    }


    @PostMapping("/sell")
    @ApiOperation(value = "GCN的卖出操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "" ,value = "")
    })
    public R<Object> sell(@RequestBody @Validated CashSellParam cashSellParam){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        boolean isOk = cashWithdrawalsService.sell(userId,cashSellParam) ;
        if(isOk){
            return R.ok("提交申请成功") ;
        }
        return R.fail("提交申请失败") ;
    }
}
