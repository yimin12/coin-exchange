package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/14 23:45
*   @Description : 
*
*/
    
/**
    * 人民币充值卡号管理
    */
@ApiModel(value="com-yimin-domain-AdminBank")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "admin_bank")
public class AdminBank {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 开户人姓名
     */
    @TableField(value = "name")
    @ApiModelProperty(value="开户人姓名")
    @NotBlank
    private String name;

    /**
     * 开户行名称
     */
    @TableField(value = "bank_name")
    @ApiModelProperty(value="开户行名称")
    @NotBlank
    private String bankName;

    /**
     * 卡号
     */
    @TableField(value = "bank_card")
    @ApiModelProperty(value="卡号")
    @NotBlank
    private String bankCard;

    /**
     * 充值转换换币种ID
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="充值转换换币种ID")
    private Long coinId;

    /**
     * 币种名称
     */
    @TableField(value = "coin_name")
    @ApiModelProperty(value="币种名称")
    private String coinName;

    /**
     * 状态：0-无效；1-有效；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：0-无效；1-有效；")
    private Byte status;
}