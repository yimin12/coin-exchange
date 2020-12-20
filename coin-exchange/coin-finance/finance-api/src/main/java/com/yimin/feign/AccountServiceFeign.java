package com.yimin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 14:34
 *   @Description :
 *
 */
@FeignClient(name = "finance-service", contextId = "accountServiceFeign", configuration = OAuth2FeignConfig.class, path = "/account")
public interface AccountServiceFeign {

    /**
     * 锁定用户的余额
     * @param userId
     * 用户的id
     * @param coinId
     * 币种的id
     * @param mum
     * 锁定的数量
     * @param type
     * 业务类型
     * @param orderId
     * 订单编号
     * @param fee
     * 手续费
     */
    @PostMapping("/lockUserAmount")
    void lockUserAmount(@RequestParam("userId") Long userId, @RequestParam("coinId")  Long coinId, @RequestParam("mum") BigDecimal mum, @RequestParam("type")String type, @RequestParam("orderId") Long orderId, @RequestParam("fee") BigDecimal fee);

    /**
     * 划转买入的账户余额
     * @param fromUserId
     * @param toUserId
     * @param coinId
     * @param amount
     * @param businessType
     * @param orderId
     */
    @PostMapping("/transferBuyAmount")
    void transferBuyAmount(@RequestParam("fromUserId") Long fromUserId, @RequestParam("toUserId")Long toUserId, @RequestParam("coinId")Long coinId, @RequestParam("amount")BigDecimal amount,@RequestParam("businessType")String businessType, @RequestParam("orderId")Long orderId);

    /**
     *  划转出售的成功的账户余额
     * @param fromUserId
     * @param toUserId
     * @param coinId
     * @param amount
     * @param businessType
     * @param orderId
     */
    @PostMapping("/transferSellAmount")
    void transferSellAmount(@RequestParam("fromUserId") Long fromUserId, @RequestParam("toUserId")Long toUserId, @RequestParam("coinId")Long coinId, @RequestParam("amount")BigDecimal amount,@RequestParam("businessType")String businessType, @RequestParam("orderId")Long orderId);
}
