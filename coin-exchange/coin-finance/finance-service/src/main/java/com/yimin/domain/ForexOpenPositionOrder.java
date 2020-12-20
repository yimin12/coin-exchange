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
    * 开仓订单信息
    */
@ApiModel(value="com-yimin-domain-ForexOpenPositionOrder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "forex_open_position_order")
public class ForexOpenPositionOrder {
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
     * 结算币种
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="结算币种")
    private Long coinId;

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
     * 委托订单
     */
    @TableField(value = "entrust_order_id")
    @ApiModelProperty(value="委托订单")
    private Long entrustOrderId;

    /**
     * 成交订单号
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="成交订单号")
    private Long orderId;

    /**
     * 成交价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value="成交价格")
    private BigDecimal price;

    /**
     * 成交数量
     */
    @TableField(value = "num")
    @ApiModelProperty(value="成交数量")
    private BigDecimal num;

    /**
     * 扣除保证金
     */
    @TableField(value = "lock_margin")
    @ApiModelProperty(value="扣除保证金")
    private BigDecimal lockMargin;

    /**
     * 平仓量
     */
    @TableField(value = "close_num")
    @ApiModelProperty(value="平仓量")
    private BigDecimal closeNum;

    /**
     * 状态：1：未平仓；2-已平仓
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：1：未平仓；2-已平仓")
    private Byte status;

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