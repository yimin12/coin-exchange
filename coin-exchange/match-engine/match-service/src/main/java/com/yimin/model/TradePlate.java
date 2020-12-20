package com.yimin.model;

import com.yimin.domain.DepthItemVo;
import com.yimin.enums.OrderDirection;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 交易的盘口数据,以后前端可以查询该数据
 */
@Data
public class TradePlate {

    /**
     * 判断数据的详情
     */
    private LinkedList<DepthItemVo> items = new LinkedList<DepthItemVo>();
    /**
     * 最大支持的深度
     */
    private int maxDepth = 100;

    /**
     * 订单的方向
     */
    private OrderDirection direction;

    /**
     * 交易对
     */
    private String symbol;

    public TradePlate(String symbol, OrderDirection direction) {
        this.symbol = symbol;
        this.direction = direction;
    }


    /**
     * 添加订单数据到盘口数据里面
     * 当我们新增一个委托单时: 它没有全部的成交 ,因此把它展示在盘口里面
     *
     * @param order
     */
    public void add(Order order) {
        if (order.getOrderDirection() != direction) {
            return;
        }
        int i = 0;
        for (i = 0; i < items.size(); i++) {
            // 1 我们的sell 队列是: 从小到大
            // 2 我们的buy 队列是: 从大到小
            DepthItemVo depthItemVo = items.get(i);
            if (
                    (direction == OrderDirection.BUY && order.getPrice().compareTo(depthItemVo.getPrice()) == -1)
                            ||
                            (direction == OrderDirection.SELL && order.getPrice().compareTo(depthItemVo.getPrice()) == 1)

            ) {
                // 还不能插入,往前走一步
                continue;
            } else if (depthItemVo.getPrice().compareTo(order.getPrice()) == 0) {
                depthItemVo.setVolume(depthItemVo.getVolume().add(order.getAmount().subtract(order.getTradedAmount())));
                return;
            } else {
                break; // 我就想插入 当前我就在第 i
            }
        }

        if (i < maxDepth) {
            DepthItemVo depthItemVo = new DepthItemVo();
            depthItemVo.setPrice(order.getPrice());
            depthItemVo.setVolume(order.getAmount().subtract(order.getTradedAmount()));
            items.add(i, depthItemVo);
        }
    }


    /**
     * 从盘口里面移除订单
     *
     * @param order
     */
    public void remove(Order order) {
        // order.getAmount().subtract(order.getTradedAmount() 成交的数量
        remove(order, order.getAmount().subtract(order.getTradedAmount()));
    }

    /***
     * 从盘口里面移除数据
     * @param order
     * @param amount
     */
    public void remove(Order order, BigDecimal amount) {

        if (items.size() == 0) {
            return;
        }
        if (order.getOrderDirection() != direction) {
            return;
        }
        Iterator<DepthItemVo> iterator = items.iterator();
        while (iterator.hasNext()) {
            DepthItemVo next = iterator.next();
            if (order.getPrice().compareTo(next.getPrice()) == 0) { // 价格相同
                next.setVolume(next.getVolume().subtract(amount));
                if (next.getVolume().compareTo(BigDecimal.ZERO) <= 0) {
                    iterator.remove(); // 若价格为 0 后,我们直接可以摘掉它
                }
            }

        }

    }
}
