package com.yimin.service;

import com.yimin.domain.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimin.vo.SymbolAssetVo;
import com.yimin.vo.UserTotalAccountVo;

import java.math.BigDecimal;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
public interface AccountService extends IService<Account>{

    /**
     * 查询某个用户的货币资产
     * @param userId
     * 用户的id
     * @param coinName
     * 货币的名称
     * @return
     */
    Account findByUserAndCoin(Long userId, String coinName);

    /**
     * 暂时锁定用户的资产
     * @param userId
     * 用户的id
     * @param coinId
     * 币种的id
     * @param mum
     * 锁定的金额
     * @param type
     * 资金流水的类型
     * @param orderId
     * 订单的Id
     * @param fee
     * 本次操作的手续费
     */
    void lockUserAmount(Long userId, Long coinId, BigDecimal mum, String type, Long orderId, BigDecimal fee);

    /**
     * 计算用户的总的资产
     * @param userId
     * @return
     */
    UserTotalAccountVo getUserTotalAccount(Long userId);

    /**
     * 统计用户交易对的资产
     * @param symbol
     *  交易对的Symbol
     * @param userId
     *      用户的Id
     * @return
     */
    SymbolAssetVo getSymbolAssert(String symbol, Long userId);


    /**
     * 用户资金的划转
     * @param adminId
     * @param userId
     * @param coinId
     * @param num
     * @param fee
     * @param remark
     * @param businessType
     * @param direction
     * @return
     */
    Boolean transferAccountAmount(Long adminId, Long userId, Long coinId,Long orderNum , BigDecimal num, BigDecimal fee,String remark,String businessType,Byte direction);

    /**
     * 给用户扣减钱
     * @param adminId
     *   操作的人
     * @param userId
     * 用户的id
     * @param coinId
     * 币种的id
     * @param orderNum
     * 订单的编号
     * @param num
     * 扣减的余额
     * @param fee
     * 费用
     * @param remark
     * 备注
     * @param businessType
     * 业务类型
     * @param direction
     * 方向
     * @return
     */
    Boolean decreaseAccountAmount(Long adminId, Long userId, Long coinId, Long orderNum ,BigDecimal num, BigDecimal fee,String remark, String businessType, byte direction);

    /**
     *
     * @param fromUserId
     * @param toUserId
     * @param coinId
     * @param amount
     * @param businessType
     * @param orderId
     */
    void transferBuyAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId);

    /**
     *
     * @param fromUserId
     * @param toUserId
     * @param coinId
     * @param amount
     * @param businessType
     * @param orderId
     */
    void transferSellAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId);
}
