package com.yimin.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * {"coinId":"1012972373649002497","mum":"99.00","num":"100","payPassword":"7fa8282ad93047a4d6fe6111c93b308a","validateCode":"111111"}
 */
@Data
@ApiModel(value = "GCN卖出的参数")
public class CashSellParam {

    @ApiModelProperty(value = "要卖出的币的ID")
    @NotNull
    private Long coinId ;

    @ApiModelProperty(value = "要卖出的币的金额")
    @NotNull
    private BigDecimal mum ;

    @ApiModelProperty(value = "要卖出的币的数量")
    @NotNull
    private BigDecimal num ;

    @ApiModelProperty(value = "支付密码")
    @NotBlank
    private String payPassword ;

    @ApiModelProperty(value = "手机验证码")
    @NotBlank
    private String validateCode ;
}
