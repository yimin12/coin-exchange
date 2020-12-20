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
    * 币种余额
    */
@ApiModel(value="com-yimin-domain-CoinBalance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "coin_balance")
public class CoinBalance {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 币种ID
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="币种ID")
    private Long coinId;

    /**
     * 币种名称
     */
    @TableField(value = "coin_name")
    @ApiModelProperty(value="币种名称")
    private String coinName;

    /**
     * 系统余额（根据充值提币计算）
     */
    @TableField(value = "system_balance")
    @ApiModelProperty(value="系统余额（根据充值提币计算）")
    private BigDecimal systemBalance;

    /**
     * 币种类型
     */
    @TableField(value = "coin_type")
    @ApiModelProperty(value="币种类型")
    private String coinType;

    /**
     * 归集账户余额
     */
    @TableField(value = "collect_account_balance")
    @ApiModelProperty(value="归集账户余额")
    private BigDecimal collectAccountBalance;

    /**
     * 钱包账户余额
     */
    @TableField(value = "loan_account_balance")
    @ApiModelProperty(value="钱包账户余额")
    private BigDecimal loanAccountBalance;

    /**
     * 手续费账户余额(eth转账需要手续费)
     */
    @TableField(value = "fee_account_balance")
    @ApiModelProperty(value="手续费账户余额(eth转账需要手续费)")
    private BigDecimal feeAccountBalance;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="更新时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;

    @TableField(value = "recharge_account_balance")
    @ApiModelProperty(value="")
    private BigDecimal rechargeAccountBalance;
}