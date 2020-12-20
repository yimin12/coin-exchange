package com.yimin.controller;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 12:33
 *   @Description :
 *
 */

import com.yimin.domain.Account;
import com.yimin.feign.AccountServiceFeign;
import com.yimin.model.R;
import com.yimin.service.AccountService;
import com.yimin.vo.UserTotalAccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@Api(tags = "资产服务的控制器")
public class AccountController implements AccountServiceFeign {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{coinName}")
    @ApiOperation(value = "获取当前用户的货币的资产情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinName" ,value = "货币的名称")
    })
    public R<Account> getUserAccount(@PathVariable("coinName")String coinName){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Account account = accountService.findByUserAndCoin(userId,coinName) ;
        return R.ok(account) ;
    }

    @GetMapping("/total")
    @ApiOperation(value = "计算用户的总资产")
    public R<UserTotalAccountVo> total() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserTotalAccountVo userTotalAccountVo = accountService.getUserTotalAccount(userId);
        return R.ok(userTotalAccountVo);
    }


    /**
     * 锁定用户的余额
     *
     * @param userId  用户的id
     * @param coinId  币种的id
     * @param mum     锁定的数量
     * @param type    业务类型
     * @param orderId 订单编号
     * @param fee
     */
    @Override
    public void lockUserAmount(Long userId, Long coinId, BigDecimal mum, String type, Long orderId, BigDecimal fee) {
        accountService.lockUserAmount(userId, coinId, mum, type, orderId, fee);
    }

    /**
     * 划转买入的账户余额
     *
     * @param fromUserId
     * @param toUserId
     * @param coinId
     * @param amount
     * @param businessType
     * @param orderId
     */
    @Override
    public void transferBuyAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        accountService.transferBuyAmount(fromUserId, toUserId, coinId, amount, businessType, orderId);
    }

    /**
     * 划转出售的成功的账户余额
     *
     * @param fromUserId
     * @param toUserId
     * @param coinId
     * @param amount
     * @param businessType
     * @param orderId
     */
    @Override
    public void transferSellAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        accountService.transferSellAmount(fromUserId, toUserId, coinId, amount, businessType, orderId);
    }
}

