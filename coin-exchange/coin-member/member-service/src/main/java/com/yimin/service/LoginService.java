package com.yimin.service;

import com.yimin.model.LoginForm;
import com.yimin.model.LoginUser;

public interface LoginService {

    /**
     * 会员的登录
     * @param loginForm
     * 登录的表单参数
     * @return
     * 登录的结果
     */
    LoginUser login(LoginForm loginForm);
}
