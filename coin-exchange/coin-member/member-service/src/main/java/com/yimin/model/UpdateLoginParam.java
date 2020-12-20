package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "修改用户登录密码的参数")
public class UpdateLoginParam {


    @ApiModelProperty(value = "原始密码")
    @NotBlank
    private String oldpassword ;


    @ApiModelProperty(value = "新的密码")
    @NotBlank
    private String newpassword ;


    @ApiModelProperty(value = "手机验证码")
    @NotBlank
    private String validateCode ;
}
