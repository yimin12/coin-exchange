package com.yimin.controller;

import com.yimin.domain.Sms;
import com.yimin.model.R;
import com.yimin.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/16 19:01
 *   @Description :
 *
 */
@RestController
@RequestMapping("/sms")
@Api(tags = "sms短信发送平台")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/sendTo")
    @ApiOperation(value = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms", value = "短信的json 数据")
    })
    public R sendMsg(@RequestBody @Validated Sms sms){
        boolean flag = smsService.sendSms(sms);
        if(flag){
            return R.ok();
        }
        return R.fail("发送失败");
    }
}
