package com.yimin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "合并的深度")
public class MergeDeptVo {

    /**
     * 合并类型
     */
    @ApiModelProperty(value = "合并类型")
    private String mergeType;

    /**
     * 合并精度
     */
    @ApiModelProperty(value = "合并精度")
    private BigDecimal value;
}
