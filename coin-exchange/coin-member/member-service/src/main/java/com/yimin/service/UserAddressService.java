package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
public interface UserAddressService extends IService<UserAddress>{


    /**
     * 通过用户的Id 分页查询用户的钱包地址
     * @param page
     * 分页参数
     * @param userId
     * 用户的Id
     * @return
     */
    Page<UserAddress> findByPage(Page<UserAddress> page, Long userId);

    /**
     * 获取用户的提供地址
     * @param userId
     * @return
     */
    List<UserAddress> getUserAddressByUserId(Long userId);


    /**
     * 使用用户的Id 和币种的Id 查询用户的充币地址
     * @param userId
     * @param coinId
     * @return
     */
    UserAddress getUserAddressByUserIdAndCoinId(String userId, Long coinId);
}
