package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CashWithdrawals;
import com.yimin.domain.CoinWithdraw;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
public interface CoinWithdrawService extends IService<CoinWithdraw>{


    /**
     * 分页条件查询充值记录
     * @param page
     * 分页参数
     * @param coinId
     * 币种的Id
     * @param userId
     * 用户的Id
     * @param userName
     * 用户的名称
     * @param mobile
     * 用户的手机号
     * @param status
     * 充值的状态
     * @param numMin
     * 充值的最小金额
     * @param numMax
     * 充值的最大金额
     * @param startTime
     * 充值的开始时间
     * @param endTime
     * 充值的截至时间
     * @return
     */
    Page<CoinWithdraw> findByPage(Page<CoinWithdraw> page, Long coinId, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime);

    /***
     * 查询用户的提币记录
     * @param userId
     * @param coinId
     * @param page
     * @return
     */
    Page<CoinWithdraw> findUserCoinWithdraw(Long userId, Long coinId, Page<CoinWithdraw> page);
}
