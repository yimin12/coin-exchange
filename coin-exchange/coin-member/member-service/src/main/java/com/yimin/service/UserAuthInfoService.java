package com.yimin.service;

import com.yimin.domain.UserAuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
public interface UserAuthInfoService extends IService<UserAuthInfo>{

    /**
     * 用户未被认证,我们需要通过用户的ID 查询用户的额认证列表
     * @param id
     * @return
     */
    List<UserAuthInfo> getUserAuthInfoByUserId(Long id);

    /**
     * 通过认证的Code 来查询用户的认证详情
     * @param authCode
     * 认证的唯一Code
     * @return
     */
    List<UserAuthInfo> getUserAuthInfoByCode(Long authCode);
}
