package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 22:30
 *   @Description :
 *      登录接口发送表单涵盖的参数
 */
@Data
@ApiModel(value = "登录的表单参数")
public class LoginForm extends GeetestForm{

    @ApiModelProperty(value = "国家的电话编号")
    private String countryCode ;

    @ApiModelProperty(value = "用户名称")
    @NotBlank
    private String username ;


    @ApiModelProperty(value = "用户密码")
    @NotBlank
    private String password ;

    @ApiModelProperty(value = "用户的uuid")
    private String uuid ;

}
