package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    * 监测当前服务器Ip状态
    */
@ApiModel(value="com-yimin-domain-CoinServer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "coin_server")
public class CoinServer {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增id")
    private Long id;

    /**
     * 钱包服务器ip
     */
    @TableField(value = "rpc_ip")
    @ApiModelProperty(value="钱包服务器ip")
    private String rpcIp;

    /**
     * 钱包服务器ip
     */
    @TableField(value = "rpc_port")
    @ApiModelProperty(value="钱包服务器ip")
    private String rpcPort;

    /**
     * 服务是否运行 0:正常,1:停止
     */
    @TableField(value = "running")
    @ApiModelProperty(value="服务是否运行 0:正常,1:停止")
    private Integer running;

    /**
     * 钱包服务器区块高度
     */
    @TableField(value = "wallet_number")
    @ApiModelProperty(value="钱包服务器区块高度")
    private Long walletNumber;

    @TableField(value = "coin_name")
    @ApiModelProperty(value="")
    private String coinName;

    /**
     * 备注信息
     */
    @TableField(value = "mark")
    @ApiModelProperty(value="备注信息")
    private String mark;

    /**
     * 真实区块高度
     */
    @TableField(value = "real_number")
    @ApiModelProperty(value="真实区块高度")
    private Long realNumber;

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
}