package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.AccountDetail;
import com.yimin.model.R;
import com.yimin.service.AccountDetailService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 23:11
 *   @Description :
 *
 */
@RestController
@RequestMapping("/accountDetails")
public class AccountDetailController {

    @Autowired
    private AccountDetailService accountDetailService;

    @GetMapping("/records")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "accountId", value = "账号的Id"),
            @ApiImplicitParam(name = "coinId", value = "当前页"),
            @ApiImplicitParam(name = "userId", value = "用户的ID"),
            @ApiImplicitParam(name = "userName", value = "用户的名称"),
            @ApiImplicitParam(name = "mobile", value = "用户的手机号"),
            @ApiImplicitParam(name = "amountStart", value = "金额的最小值"),
            @ApiImplicitParam(name = "amountEnd", value = "金额的最小值"),
            @ApiImplicitParam(name = "startTime", value = "充值开始时间"),
            @ApiImplicitParam(name = "endTime", value = "充值结束时间"),
    })
    public R<Page<AccountDetail>> findByPage(
            @ApiIgnore Page<AccountDetail> page, String amountStart, String amountEnd,
            Long userId, String userName, String mobile,
            Long coinId, Long accountId, String startTime, String endTime
    ) {
        Page<AccountDetail> pageData = accountDetailService.findByPage(page, coinId, accountId, userId, userName, mobile,
                amountStart, amountEnd, startTime, endTime
        );
        return R.ok(pageData);
    }
}
