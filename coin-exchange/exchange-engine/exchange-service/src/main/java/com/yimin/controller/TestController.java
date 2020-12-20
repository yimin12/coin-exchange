package com.yimin.controller;

import com.yimin.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 19:39
 *   @Description :
 *
 */
@RestController
@Api(tags = "交易系统的测试")
public class TestController {


    @GetMapping("/test")
    @ApiOperation(value = "交易系统的测试")
    public R<String> test(){
        return R.ok("交易系统的测试成功");
    }
}
