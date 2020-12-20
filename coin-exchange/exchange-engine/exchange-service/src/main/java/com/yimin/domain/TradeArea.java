package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 14:53
*   @Description : 
*
*/
    
/**
    * 交易区
    */
@ApiModel(value="com-yimin-domain-TradeArea")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "trade_area")
public class TradeArea {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 交易区名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="交易区名称")
    @NotBlank
    private String name;

    /**
     * 交易区代码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="交易区代码")
    @NotBlank
    private String code;

    /**
     * 类型：1-数字货币交易；2-创新交易使用；
     */
    @TableField(value = "type")
    @ApiModelProperty(value="类型：1-数字货币交易；2-创新交易使用；")
    private Byte type;

    /**
     * 结算币种（仅创新交易需要使用）
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="结算币种（仅创新交易需要使用）")
    private Long coinId;

    /**
     * 结算币种名称（仅创新交易需要使用）
     */
    @TableField(value = "coin_name")
    @ApiModelProperty(value="结算币种名称（仅创新交易需要使用）")
    private String coinName;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Byte sort;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private Byte status;

    /**
     * 是否作为基础结算货币,0否1是 供统计个人账户使用
     */
    @TableField(value = "base_coin")
    @ApiModelProperty(value="是否作为基础结算货币,0否1是 供统计个人账户使用")
    private Long baseCoin;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;
}