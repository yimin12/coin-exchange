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
*   @Date : 2020/12/19 21:12
*   @Description : 
*
*/
    
/**
    * 委托订单信息
    */
@ApiModel(value="com-yimin-domain-EntrustOrder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "entrust_order")
public class EntrustOrder {
    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="订单ID")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 市场ID
     */
    @TableField(value = "market_id")
    @ApiModelProperty(value="市场ID")
    private Long marketId;

    /**
     * 市场类型（1：币币交易，2：创新交易）
     */
    @TableField(value = "market_type")
    @ApiModelProperty(value="市场类型（1：币币交易，2：创新交易）")
    private Byte marketType;

    /**
     * 交易对标识符
     */
    @TableField(value = "symbol")
    @ApiModelProperty(value="交易对标识符")
    private String symbol;

    /**
     * 交易市场
     */
    @TableField(value = "market_name")
    @ApiModelProperty(value="交易市场")
    private String marketName;

    /**
     * 委托价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value="委托价格")
    private BigDecimal price;

    /**
     * 合并深度价格1
     */
    @TableField(value = "merge_low_price")
    @ApiModelProperty(value="合并深度价格1")
    private BigDecimal mergeLowPrice;

    /**
     * 合并深度价格2
     */
    @TableField(value = "merge_high_price")
    @ApiModelProperty(value="合并深度价格2")
    private BigDecimal mergeHighPrice;

    /**
     * 委托数量
     */
    @TableField(value = "volume")
    @ApiModelProperty(value="委托数量")
    private BigDecimal volume;

    /**
     * 委托总额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value="委托总额")
    private BigDecimal amount;

    /**
     * 手续费比率
     */
    @TableField(value = "fee_rate")
    @ApiModelProperty(value="手续费比率")
    private BigDecimal feeRate;

    /**
     * 交易手续费
     */
    @TableField(value = "fee")
    @ApiModelProperty(value="交易手续费")
    private BigDecimal fee;

    /**
     * 合约单位
     */
    @TableField(value = "contract_unit")
    @ApiModelProperty(value="合约单位")
    private Integer contractUnit;

    /**
     * 成交数量
     */
    @TableField(value = "deal")
    @ApiModelProperty(value="成交数量")
    private BigDecimal deal;

    /**
     * 冻结量
     */
    @TableField(value = "freeze")
    @ApiModelProperty(value="冻结量")
    private BigDecimal freeze;

    /**
     * 保证金比例
     */
    @TableField(value = "margin_rate")
    @ApiModelProperty(value="保证金比例")
    private BigDecimal marginRate;

    /**
     * 基础货币对（USDT/BTC）兑换率
     */
    @TableField(value = "base_coin_rate")
    @ApiModelProperty(value="基础货币对（USDT/BTC）兑换率")
    private BigDecimal baseCoinRate;

    /**
     * 报价货币对（USDT/BTC)兑换率
     */
    @TableField(value = "price_coin_rate")
    @ApiModelProperty(value="报价货币对（USDT/BTC)兑换率")
    private BigDecimal priceCoinRate;

    /**
     * 占用保证金
     */
    @TableField(value = "lock_margin")
    @ApiModelProperty(value="占用保证金")
    private BigDecimal lockMargin;

    /**
     * 价格类型：1-市价；2-限价
     */
    @TableField(value = "price_type")
    @ApiModelProperty(value="价格类型：1-市价；2-限价")
    private Byte priceType;

    /**
     * 交易类型：1-开仓；2-平仓
     */
    @TableField(value = "trade_type")
    @ApiModelProperty(value="交易类型：1-开仓；2-平仓")
    private Byte tradeType;

    /**
     * 买卖类型：1-买入；2-卖出
2 卖出

     */
    @TableField(value = "type")
    @ApiModelProperty(value="买卖类型：1-买入；2-卖出,2 卖出,")
    private Byte type;

    /**
     * 平仓委托关联单号
     */
    @TableField(value = "open_order_id")
    @ApiModelProperty(value="平仓委托关联单号")
    private Long openOrderId;

    /**
     * status
0未成交
1已成交
2已取消
4异常单
     */
    @TableField(value = "status")
    @ApiModelProperty(value="status,0未成交,1已成交,2已取消,4异常单")
    private Byte status;

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
}