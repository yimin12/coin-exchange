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
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
/**
    * 数字货币充值记录
    */
@ApiModel(value="com-yimin-domain-CoinRecharge")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "coin_recharge")
public class CoinRecharge {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增id")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 币种id
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="币种id")
    private Long coinId;

    /**
     * 币种名称
     */
    @TableField(value = "coin_name")
    @ApiModelProperty(value="币种名称")
    private String coinName;

    /**
     * 币种类型
     */
    @TableField(value = "coin_type")
    @ApiModelProperty(value="币种类型")
    private String coinType;

    /**
     * 钱包地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="钱包地址")
    private String address;

    /**
     * 充值确认数
     */
    @TableField(value = "confirm")
    @ApiModelProperty(value="充值确认数")
    private Integer confirm;

    /**
     * 状态：0-待入帐；1-充值失败，2到账失败，3到账成功；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：0-待入帐；1-充值失败，2到账失败，3到账成功；")
    private Integer status;

    /**
     * 交易id
     */
    @TableField(value = "txid")
    @ApiModelProperty(value="交易id")
    private String txid;

    @TableField(value = "amount")
    @ApiModelProperty(value="")
    private BigDecimal amount;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户的名称")
    private String username  ;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户的真实名称")
    private String realName ;
}