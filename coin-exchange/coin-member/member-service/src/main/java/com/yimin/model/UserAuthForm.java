package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "用户的身份认证信息")
public class UserAuthForm extends GeetestForm{


    @NotBlank
    @ApiModelProperty(value = "用户的真实名称")
    private String realName ;

    @NotNull
    @ApiModelProperty(value = "用户的证件类型")
    private Integer idCardType ;

    @NotBlank
    @ApiModelProperty(value = "用户的证件号码")
    private String idCard ;

}
