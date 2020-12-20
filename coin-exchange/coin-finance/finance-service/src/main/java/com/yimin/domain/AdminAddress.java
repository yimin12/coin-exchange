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
import javax.validation.constraints.NotNull;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 16:13
*   @Description : 
*
*/
    
/**
    * 平台归账手续费等账户
    */
@ApiModel(value="com-yimin-domain-AdminAddress")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "admin_address")
public class AdminAddress {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="编号")
    private Long id;

    /**
     * 币种Id
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="币种Id")
    @NotNull
    private Long coinId;

    /**
     * eth keystore
     */
    @TableField(value = "keystore")
    @ApiModelProperty(value="eth keystore")
    private String keystore;

    /**
     * eth账号密码
     */
    @TableField(value = "pwd")
    @ApiModelProperty(value="eth账号密码")
    private String pwd;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * 1:归账(冷钱包地址),2:打款,3:手续费
     */
    @TableField(value = "status")
    @ApiModelProperty(value="1:归账(冷钱包地址),2:打款,3:手续费")
    private Integer status;

    /**
     * 类型
     */
    @TableField(value = "coin_type")
    @ApiModelProperty(value="类型")
//    @NotBlank
    private String coinType;
}