package com.yimin.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value = "下委托单的操作")
public class OrderParam {


    @NotBlank
    @ApiModelProperty(value = "交易对的符号")
    private String symbol ;


    @NotNull
    @ApiModelProperty(value = "价格")
    private BigDecimal price ;

    @NotNull
    @ApiModelProperty(value = "数量")
    private BigDecimal volume ;


    @NotNull
    @ApiModelProperty(value = "类型: 1 :  买入 2 :卖出")
    private Integer  type ;

}
