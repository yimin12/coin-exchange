package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 14:53
*   @Description : 
*
*/
    
/**
    * 成交数据
    */
@ApiModel(value="com-yimin-domain-TurnoverRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "turnover_record")
public class TurnoverRecord {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 市场ID
     */
    @TableField(value = "market_id")
    @ApiModelProperty(value="市场ID")
    private Long marketId;

    /**
     * 成交价
     */
    @TableField(value = "price")
    @ApiModelProperty(value="成交价")
    private BigDecimal price;

    /**
     * 成交数量
     */
    @TableField(value = "volume")
    @ApiModelProperty(value="成交数量")
    private BigDecimal volume;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;
}