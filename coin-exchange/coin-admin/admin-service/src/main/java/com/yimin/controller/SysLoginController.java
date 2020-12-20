package com.yimin.controller;

import com.yimin.model.LoginResult;
import com.yimin.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 19:14
 *   @Description :
 *      登陆的控制器
 */
@RestController
@Api(tags = "登录的控制器")
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    /**
     * 根据用户名密码来进行登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login") // /admin/login will be transferred to /login by gateway
    @ApiOperation(value = "后台管理人员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名称"),
            @ApiImplicitParam(name = "password",value = "用户的密码")
    })
    public LoginResult login(@RequestParam(required = true) String username, @RequestParam(required = true) String password){
        return loginService.login(username, password);
    }
}
