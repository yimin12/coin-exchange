package com.yimin.service;

import com.yimin.model.LoginResult;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 19:22
 *   @Description :
 *      登录接口
 */
public interface SysLoginService {

    /**
     * 登录的接口
     * @param username 登录用户名
     * @param password 登录密码
     * @return 登录的结果
     */
    LoginResult login(String username, String password);
}
