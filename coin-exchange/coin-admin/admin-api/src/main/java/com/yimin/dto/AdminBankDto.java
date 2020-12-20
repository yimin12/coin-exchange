package com.yimin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 14:11
 *   @Description :
 *
 */
@Data
@ApiModel(value = "银行卡的参数")
public class AdminBankDto {

    @ApiModelProperty(value = "开户行的人名称")
    private String name ;

    @ApiModelProperty(value = "开户行的银行名称")
    private String bankName ;

    @ApiModelProperty(value = "开户行的银行卡号")
    private String bankCard ;
}
