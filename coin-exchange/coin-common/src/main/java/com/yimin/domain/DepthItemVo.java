package com.yimin.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel("明细")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepthItemVo implements Comparable<DepthItemVo> {

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price = BigDecimal.ZERO;;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal volume = BigDecimal.ZERO;


    @Override
    public int compareTo(DepthItemVo o) {
        return this.price.compareTo(o.getPrice());
    }
}
