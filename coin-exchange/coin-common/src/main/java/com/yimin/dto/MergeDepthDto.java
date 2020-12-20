package com.yimin.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MergeDepthDto {

    /**
     * 合并类型
     */
    private String mergeType;

    /**
     * 合并精度
     */
    private BigDecimal value;
}