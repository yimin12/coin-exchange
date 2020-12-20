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
    * 平仓详情
    */
@ApiModel(value="com-yimin-domain-ForexClosePositionOrder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "forex_close_position_order")
public class ForexClosePositionOrder {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 交易对ID
     */
    @TableField(value = "market_id")
    @ApiModelProperty(value="交易对ID")
    private Long marketId;

    /**
     * 交易对名称
     */
    @TableField(value = "market_name")
    @ApiModelProperty(value="交易对名称")
    private String marketName;

    /**
     * 持仓方向：1-买；2-卖
     */
    @TableField(value = "type")
    @ApiModelProperty(value="持仓方向：1-买；2-卖")
    private Byte type;

    /**
     * 资金账户ID
     */
    @TableField(value = "account_id")
    @ApiModelProperty(value="资金账户ID")
    private Long accountId;

    /**
     * 委托订单号
     */
    @TableField(value = "entrust_order_id")
    @ApiModelProperty(value="委托订单号")
    private Long entrustOrderId;

    /**
     * 成交订单号
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="成交订单号")
    private Long orderId;

    /**
     * 成交价
     */
    @TableField(value = "price")
    @ApiModelProperty(value="成交价")
    private BigDecimal price;

    /**
     * 成交数量
     */
    @TableField(value = "num")
    @ApiModelProperty(value="成交数量")
    private BigDecimal num;

    /**
     * 关联开仓订单号
     */
    @TableField(value = "open_id")
    @ApiModelProperty(value="关联开仓订单号")
    private Long openId;

    /**
     * 平仓盈亏
     */
    @TableField(value = "profit")
    @ApiModelProperty(value="平仓盈亏")
    private BigDecimal profit;

    /**
     * 返回还保证金
     */
    @TableField(value = "unlock_margin")
    @ApiModelProperty(value="返回还保证金")
    private BigDecimal unlockMargin;

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