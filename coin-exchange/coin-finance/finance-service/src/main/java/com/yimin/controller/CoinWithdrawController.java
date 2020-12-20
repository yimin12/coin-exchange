package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CoinWithdraw;
import com.yimin.model.R;
import com.yimin.service.CoinWithdrawService;
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
 *   @Date : 2020/12/17 22:46
 *   @Description :
 *
 */
@RestController
@RequestMapping("/coinWithdraws")
@Api(tags = "提币记录的审核")
public class CoinWithdrawController {


    @Autowired
    private CoinWithdrawService coinWithdrawService ;

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
    public R<Page<CoinWithdraw>> findByPage(
            @ApiIgnore Page<CoinWithdraw> page, Long coinId,
            Long userId, String userName, String mobile,
            Byte status, String numMin, String numMax,
            String startTime, String endTime
    ) {
        Page<CoinWithdraw> pageData = coinWithdrawService.findByPage(page,coinId,userId,userName,
                mobile,status,numMin,numMax,startTime,endTime) ;
        return R.ok(pageData) ;
    }

    /**
     * size=10&current=1&coinId=
     * @return
     */
    @GetMapping("/user/record")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current" ,value = "当前页"),
            @ApiImplicitParam(name = "size" ,value = "每页显示的条数"),
            @ApiImplicitParam(name = "coinId" ,value = "币种的Id"),
    })
    @ApiOperation(value = "查询用户的提币记录")
    public R<Page<CoinWithdraw>> findUserCoinWithdraw(@ApiIgnore Page<CoinWithdraw> page ,Long coinId){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<CoinWithdraw> coinWithdrawPage = coinWithdrawService.findUserCoinWithdraw(userId,coinId,page) ;
        return R.ok(coinWithdrawPage) ;
    }
}
