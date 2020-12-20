package com.yimin.controller;

import com.lmax.disruptor.EventHandler;
import com.yimin.disruptor.OrderEvent;
import com.yimin.disruptor.OrderEventHandler;
import com.yimin.domain.DepthItemVo;
import com.yimin.enums.OrderDirection;
import com.yimin.feign.OrderBooksFeignClient;
import com.yimin.model.MergeOrder;
import com.yimin.model.OrderBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 22:33
 *   @Description :
 *
 */
@RestController
public class MatchController implements OrderBooksFeignClient {

    @Autowired
    private EventHandler<OrderEvent>[] eventHandlers;


    @GetMapping("/match/order")
    public TreeMap<BigDecimal, MergeOrder> getTradeData(@RequestParam(required = true) String symbol, @RequestParam(required = true)Integer orderDirection) {
        for (EventHandler<OrderEvent> eventHandler : eventHandlers) {
            OrderEventHandler orderEventHandler = (OrderEventHandler) eventHandler;
            if (orderEventHandler.getSymbol().equals(symbol)) {
                OrderBooks orderBooks = orderEventHandler.getOrderBooks();
                return
                        orderBooks.getCurrentLimitPrices(OrderDirection.getOrderDirection(orderDirection));
            }
        }
        return null;
    }

    /**
     * 查询该交易对的盘口数据
     * key :sell:asks   value: List<DepthItemVo>
     * key:buy:bids    value:List<DepthItemVo>
     *
     * @param symbol
     * @return
     */
    @Override
    public Map<String, List<DepthItemVo>> querySymbolDepth(String symbol) {
        for (EventHandler<OrderEvent> eventHandler : eventHandlers) {
            OrderEventHandler orderEventHandler = (OrderEventHandler) eventHandler;
            if (orderEventHandler.getSymbol().equals(symbol)) {
                HashMap<String, List<DepthItemVo>> deptMap = new HashMap<>();
                deptMap.put("asks", orderEventHandler.getOrderBooks().getSellTradePlate().getItems());
                deptMap.put("bids", orderEventHandler.getOrderBooks().getBuyTradePlate().getItems());
                return deptMap;
            }
        }
        return null;
    }
}
