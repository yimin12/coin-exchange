package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "注册的表单数据")
public class RegisterParam extends GeetestForm {

    @ApiModelProperty(value = "国家的代号")
    @NotBlank
    private String countryCode;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "邀请码")
    private String invitionCode;

    @ApiModelProperty(value = "密码")
    @NotBlank
    private String password;

    @ApiModelProperty(value = "手机的验证码---现在被弃用了")
    private String validateCode;
}
