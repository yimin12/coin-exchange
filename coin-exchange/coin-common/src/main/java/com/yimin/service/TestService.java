package com.yimin.service;

import com.yimin.model.WebLog;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 13:30
 *   @Description :
 *
 */
public interface TestService {

    /**
     * 通过username 查询webLog日志
     */
    WebLog get(String username);
}
