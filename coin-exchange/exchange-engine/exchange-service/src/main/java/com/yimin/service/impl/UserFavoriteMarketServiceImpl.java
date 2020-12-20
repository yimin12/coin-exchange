package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.UserFavoriteMarket;
import com.yimin.mapper.UserFavoriteMarketMapper;
import com.yimin.service.UserFavoriteMarketService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/18 20:59
*   @Description : 
*
*/
    
@Service
public class UserFavoriteMarketServiceImpl extends ServiceImpl<UserFavoriteMarketMapper, UserFavoriteMarket> implements UserFavoriteMarketService{

    /**
     * 用户取消收藏
     *
     * @param marketId // 交易市场的id
     * @param userId // 用户的Id
     * @return
     */
    @Override
    public boolean deleteUserFavoriteMarket(Long marketId, Long userId) {
        return remove(new LambdaQueryWrapper<UserFavoriteMarket>()
                .eq(UserFavoriteMarket::getMarketId, marketId)
                .eq(UserFavoriteMarket::getUserId, userId)
        );
    }
}
