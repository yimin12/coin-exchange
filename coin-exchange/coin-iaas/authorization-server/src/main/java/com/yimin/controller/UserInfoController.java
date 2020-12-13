package com.yimin.controller;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 11:50
 *   @Description :
 *
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    /**
     * 当前登录的的用户对象
     * @param principal
     * @return
     */
    @GetMapping("/user/info")
    public Principal userInfo(Principal principal){

        // Method 1: Use threadLocal to implement
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Method 2: just return (OAuth2.0 will auto inject)
        return principal;
    }
}
