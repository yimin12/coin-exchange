package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
/**
    * 币种配置信息
    */
@ApiModel(value="com-yimin-domain-CoinConfig")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "coin_config")
public class CoinConfig {
    /**
     * 币种ID(对应coin表ID)
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="币种ID(对应coin表ID)")
    @NotNull
    private Long id;

    /**
     * 币种名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="币种名称")
    @NotBlank
    private String name;

    /**
     * btc-比特币系列；eth-以太坊；ethToken-以太坊代币；etc-以太经典；\r\n\r\n
     */
    @TableField(value = "coin_type")
    @ApiModelProperty(value="btc-比特币系列；eth-以太坊；ethToken-以太坊代币；etc-以太经典；\r\n\r\n")
    @NotBlank
    private String coinType;

    /**
     * 钱包最低留存的币
     */
    @TableField(value = "credit_limit")
    @ApiModelProperty(value="钱包最低留存的币")
    @NotNull
    private BigDecimal creditLimit;

    /**
     * 当触发改状态的时候,开始归集
     */
    @TableField(value = "credit_max_limit")
    @ApiModelProperty(value="当触发改状态的时候,开始归集")
    private BigDecimal creditMaxLimit;

    /**
     * rpc服务ip
     */
    @TableField(value = "rpc_ip")
    @ApiModelProperty(value="rpc服务ip")
    @NotBlank
    private String rpcIp;

    /**
     * rpc服务port
     */
    @TableField(value = "rpc_port")
    @ApiModelProperty(value="rpc服务port")
    @NotBlank
    private String rpcPort;

    /**
     * rpc用户
     */
    @TableField(value = "rpc_user")
    @ApiModelProperty(value="rpc用户")
    private String rpcUser;

    /**
     * rpc密码
     */
    @TableField(value = "rpc_pwd")
    @ApiModelProperty(value="rpc密码")
    private String rpcPwd;

    /**
     * 最后一个区块
     */
    @TableField(value = "last_block")
    @ApiModelProperty(value="最后一个区块")
    private String lastBlock;

    /**
     * 钱包用户名
     */
    @TableField(value = "wallet_user")
    @ApiModelProperty(value="钱包用户名")
    private String walletUser;

    /**
     * 钱包密码
     */
    @TableField(value = "wallet_pass")
    @ApiModelProperty(value="钱包密码")
    private String walletPass;

    /**
     * 代币合约地址
     */
    @TableField(value = "contract_address")
    @ApiModelProperty(value="代币合约地址")
    private String contractAddress;

    /**
     * context
     */
    @TableField(value = "context")
    @ApiModelProperty(value="context")
    private String context;

    /**
     * 最低确认数
     */
    @TableField(value = "min_confirm")
    @ApiModelProperty(value="最低确认数")
    private Integer minConfirm;

    /**
     * 定时任务
     */
    @TableField(value = "task")
    @ApiModelProperty(value="定时任务")
    private String task;

    /**
     * 是否可用0不可用,1可用
     */
    @TableField(value = "status")
    @ApiModelProperty(value="是否可用0不可用,1可用")
    private Integer status;

    @TableField(value = "auto_draw_limit")
    @ApiModelProperty(value="")
    private BigDecimal autoDrawLimit;

    @TableField(value = "auto_draw")
    @ApiModelProperty(value="")
    private Integer autoDraw;
}