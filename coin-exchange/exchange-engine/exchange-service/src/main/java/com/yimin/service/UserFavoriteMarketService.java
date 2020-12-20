package com.yimin.service;

import com.yimin.domain.UserFavoriteMarket;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/18 20:59
*   @Description : 
*
*/
    
public interface UserFavoriteMarketService extends IService<UserFavoriteMarket>{


    /**
     * 用户取消收藏
     * @param marketId
     * @param userId
     * @return
     */
    boolean deleteUserFavoriteMarket(Long marketId, Long userId);}
