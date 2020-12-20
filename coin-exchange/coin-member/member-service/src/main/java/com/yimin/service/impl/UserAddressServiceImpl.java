package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.UserAddressMapper;
import com.yimin.domain.UserAddress;
import com.yimin.service.UserAddressService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService{

    /**
     * 通过用户的Id 分页查询用户的钱包地址
     * @param page
     * 分页参数
     * @param userId
     * 用户的Id
     * @return
     */
    @Override
    public Page<UserAddress> findByPage(Page<UserAddress> page, Long userId) {
        return page(page, new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, userId));
    }

    /**
     * 获取用户的提供地址
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserAddress> getUserAddressByUserId(Long userId) {

        return list(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId,userId).orderByDesc(UserAddress::getCreated));
    }


    /**
     * 使用用户的Id 和币种的Id 查询用户的充币地址
     *
     * @param userId
     * @param coinId
     * @return
     */
    @Override
    public UserAddress getUserAddressByUserIdAndCoinId(String userId, Long coinId) {
        return getOne(new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getUserId,userId)
                .eq(UserAddress::getCoinId,coinId)
        );
    }
}
