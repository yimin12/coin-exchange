package com.yimin.controller;

import com.yimin.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 15:23
 *   @Description :
 *
 */
@RestController
@Api(tags = "财务系统测试")
public class TestController {

    @GetMapping("/test")
    @ApiOperation(value = "财务系统的测试接口")
    public R test(){
        return R.ok("测试成功");
    }
}
