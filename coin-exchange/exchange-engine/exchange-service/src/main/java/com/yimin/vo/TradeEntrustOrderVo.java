package com.yimin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@ApiModel(value = "未完成的委托单")
public class TradeEntrustOrderVo {

    /**
     * 订单号
     */
    @ApiModelProperty(value = "委托单的订单id")
    private Long orderId;

    /**
     * 委托类型
     */
    @ApiModelProperty(value = "委托类型")
    private Integer type;

    /**
     * 委托价格
     */
    @ApiModelProperty(value = "委托价格")
    private BigDecimal price;

    /**
     * 已成交均价
     */
    @ApiModelProperty(value = "已成交均价")
    private BigDecimal dealAvgPrice;

    /**
     * 委托数量
     */
    @ApiModelProperty(value = "委托数量")
    private BigDecimal volume;

    /**
     * 已成交量
     */
    @ApiModelProperty(value = "已成交量")
    private BigDecimal dealVolume;

    /**
     * 预计成交额
     */
    @ApiModelProperty(value = "预计成交额")
    private BigDecimal amount;

    /**
     * 成交总额
     */
    @ApiModelProperty(value = "成交总额")
    private BigDecimal dealAmount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 委托时间
     */
    @ApiModelProperty(value = "委托时间")
    private Date created;
}
