package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "重置交易密码")
public class UnsetPayPasswordParam {

    @ApiModelProperty(value = "新的交易密码")
    @NotBlank
    private String payPassword ;

    @ApiModelProperty(value = "手机的验证码")
    @NotBlank
    private String validateCode ;
}
