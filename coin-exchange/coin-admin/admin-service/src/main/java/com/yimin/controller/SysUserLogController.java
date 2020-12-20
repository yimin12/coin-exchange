package com.yimin.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.SysUserLog;
import com.yimin.model.R;
import com.yimin.service.SysUserLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 17:04
 *   @Description :
 *
 */
@RestController
@Api(tags = "用户的操作记录查询")
@RequestMapping("/sysUserLog")
public class SysUserLogController {

    @Autowired
    private SysUserLogService sysUserLogService ;

    @GetMapping
    @ApiOperation(value = "分页查询用户的操作记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page" ,value = "分页查询")
    })
    public R<Page<SysUserLog>> findByPage(@ApiIgnore  Page<SysUserLog> page){
        page.addOrder(OrderItem.desc("created")) ;
        return R.ok(sysUserLogService.page(page)) ;
    }
}
