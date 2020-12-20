package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CoinRecharge;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
public interface CoinRechargeService extends IService<CoinRecharge>{

    /**
     * 提现记录的查询
     * @param page
     * 分页数据
     * @param userId
     * 用户的id
     * @param userName
     * 用户的名称
     * @param mobile
     * 用户的手机号
     * @param status
     * 提现的状态
     * @param numMin
     * 提现的最小金额
     * @param numMax
     * 提现的最大金额
     * @param startTime
     * 提现的开始时间
     * @param endTime
     * 提现的截至时间
     * @return
     */
    Page<CoinRecharge> findByPage(Page<CoinRecharge> page, Long coinId, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime);

    /**
     * 查询用户充币记录
     * @param page
     * 分页参数
     * @param coinId
     * 币种的Id
     * @param userId
     * 用户的ID
     *
     * @return
     */
    Page<CoinRecharge> findUserCoinRecharge(Page<CoinRecharge> page, Long coinId, Long userId);
}
