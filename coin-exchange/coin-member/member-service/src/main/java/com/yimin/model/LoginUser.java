package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 22:31
 *   @Description :
 *
 */
@ApiModel(value = "登录成功的用户")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    @ApiModelProperty(value = "用户名称")
    private String username ;

    @ApiModelProperty(value = "token的过期时间")
    private Long expire ;

    @ApiModelProperty(value = "access_token")
    private String access_token ;

    @ApiModelProperty(value = "refreshToken")
    private String refresh_token ;
}
