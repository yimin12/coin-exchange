package com.yimin.controller;

import com.yimin.domain.SysUser;
import com.yimin.model.R;
import com.yimin.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 17:55
 *   @Description :
 *
 */
@RestController
@Api(tags = "后台管理系统的测试接口")
public class TestController {

    @Autowired
    private SysUserService sysUserService;


    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "查询用户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的ID")
    })
    public R<SysUser> getSysUserInfo(@PathVariable("id") Long id){
        SysUser sysUser = sysUserService.getById(id);
        return R.ok(sysUser);
    }
}
