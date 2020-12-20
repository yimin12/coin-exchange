package com.yimin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "交易市场")
@Data
public class TradeMarketVo {
    /**
     * 交易对
     */
    @ApiModelProperty(value = "交易对")
    private String symbol;

    /**
     * 市场名称
     */
    @ApiModelProperty(value = "市场名称")
    private String name;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * 买入手续费
     */
    @ApiModelProperty(value = "买入手续费")
    private BigDecimal buyFeeRate;

    /**
     * 卖出手续费
     */
    @ApiModelProperty(value = "卖出手续费")
    private BigDecimal sellFeeRate;

    /**
     * 价格小数位数
     */
    @ApiModelProperty(value = "价格小数位数")
    private int priceScale;

    /**
     * 数量小数位数
     */
    @ApiModelProperty(value = "数量小数位数")
    private int numScale;

    /**
     * 最小委托数量
     */
    @ApiModelProperty(value = " 最小委托数量")
    private BigDecimal numMin;

    /**
     * 最大的委托量
     */
    @ApiModelProperty(value = " 最大小数位数")
    private BigDecimal numMax;

    /**
     * 最小成交额
     */
    @ApiModelProperty(value = "最小成交额")
    private BigDecimal tradeMin;

    /**
     * 最大成交额
     */
    @ApiModelProperty(value = "最大成交额")
    private BigDecimal tradeMax;

    /**
     * 当前价
     */
    @ApiModelProperty(value = "当前价")
    private BigDecimal price;

    /**
     * 价格单位
     */
    @ApiModelProperty(value = "价格单位")
    private String priceUnit;

    /**
     * 当前价（人民币）
     */
    @ApiModelProperty(value = "当前价（人民币）")
    private BigDecimal cnyPrice;

    /**
     * 报价货币人民币价格
     */
    @ApiModelProperty(value = "报价货币人民币价格")
    private BigDecimal coinCnyPrice;

    /**
     * 合并深度
     */
    @ApiModelProperty(value = "合并深度")
    private List<MergeDeptVo> mergeDepth;

    /**
     * 最高价
     */
    @ApiModelProperty(value = "最高价")
    private BigDecimal high;

    /**
     * 最低价
     */
    @ApiModelProperty(value = "最低价")
    private BigDecimal low;

    /**
     * 日总交易量
     */
    @ApiModelProperty(value = "日总交易量")
    private BigDecimal volume = BigDecimal.ZERO;

    /**
     * 总成交额
     */
    @ApiModelProperty(value = "总成交额")
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 涨幅
     */
    @ApiModelProperty(value = "涨幅")
    private double change;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private int sort;
}
