package com.yimin.model;

import com.yimin.enums.OrderDirection;
import lombok.Data;

import java.math.BigDecimal;

//
/**
 * 成交记录 -- 只要成交一次,就产生一个记录
 */
@Data
public class ExchangeTrade {

    /**
     * 交易对
     */
    private String symbol;
    /**
     * 订单的方向
     */
    private OrderDirection direction;
    /**
     * 本次交易的价格
     */
    private BigDecimal price;

    /**
     * 本次交易的数量
     */
    private BigDecimal amount;

    /**
     * 本次买方的Id
     */
    private String buyOrderId;
    /**
     * 本次出售方的id
     */
    private String sellOrderId;
    /**
     * 买方的成交额
     */
    private BigDecimal buyTurnover;
    /**
     * 出售方的成交额
     */
    private BigDecimal sellTurnover;

    /**
     * 成交时间
     */
    private Long time;
}
