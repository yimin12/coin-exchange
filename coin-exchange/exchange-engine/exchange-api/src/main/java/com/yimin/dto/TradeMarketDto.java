package com.yimin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class TradeMarketDto implements Comparable<TradeMarketDto> {

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 市场名称
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 买入手续费
     */
    private BigDecimal buyFeeRate;

    /**
     * 卖出手续费
     */
    private BigDecimal sellFeeRate;

    /**
     * 价格小数位数
     */
    private int priceScale;

    /**
     * 数量小数位数
     */
    private int numScale;

    /**
     * 最小委托数量
     */
    private BigDecimal numMin;

    /**
     * 最大小数位数
     */
    private BigDecimal numMax;

    /**
     * 最小成交额
     */
    private BigDecimal tradeMin;

    /**
     * 最大成交额
     */
    private BigDecimal tradeMax;

    /**
     * 当前价
     */
    private BigDecimal price;

    /**
     * 价格单位
     */
    private String priceUnit;

    /**
     * 当前价（人民币）
     */
    private BigDecimal cnyPrice;

    /**
     * 报价货币人民币价格
     */
    private BigDecimal coinCnyPrice;

    /**
     * 合并深度
     */
//    private List<MergeDepthDto> mergeDepth;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 日总交易量
     */
    private BigDecimal volume = BigDecimal.ZERO;

    /**
     * 日总成交额
     */
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 涨幅
     */
    private double change;

    /**
     * 排序
     */
    private int sort;

    @Override
    public int compareTo(TradeMarketDto object) {
        if (this.sort >= object.getSort()) {
            return 1;
        } else {
            return -1;
        }
    }
}
