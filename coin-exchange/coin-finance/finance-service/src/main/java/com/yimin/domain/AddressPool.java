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


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 16:13
*   @Description : 
*
*/
    
/**
    * 用户的地址池
    */
@ApiModel(value="com-yimin-domain-AddressPool")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "address_pool")
public class AddressPool {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 币种ID
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="币种ID")
    private Long coinId;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * keystore
     */
    @TableField(value = "keystore")
    @ApiModelProperty(value="keystore")
    private String keystore;

    /**
     * 密码
     */
    @TableField(value = "pwd")
    @ApiModelProperty(value="密码")
    private String pwd;

    /**
     * 地址类型
     */
    @TableField(value = "coin_type")
    @ApiModelProperty(value="地址类型")
    private String coinType;
}