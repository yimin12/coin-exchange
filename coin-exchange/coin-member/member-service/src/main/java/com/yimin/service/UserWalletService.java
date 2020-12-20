package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.UserWallet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
public interface UserWalletService extends IService<UserWallet>{

    /**
     * 分页查询用户的提币地址
     * @param page
     * 分页参数
     * @param userId
     * 用户的ID
     * @return
     */
    Page<UserWallet> findByPage(Page<UserWallet> page, Long userId);


    /**
     * 查询用户的提币的地址
     * @param userId
     * 用户的Id
     * @param coinId
     * 币种的Id
     * @return
     */
    List<UserWallet> findUserWallets(Long userId, Long coinId);
    /**
     * 删除用户的提现地址
     * @param addressId
     * 提现地址的Id
     * @param payPassword
     * 交易密码
     * @return
     */
    boolean deleteUserWallet(Long addressId, String payPassword);

}
