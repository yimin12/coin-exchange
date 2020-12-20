package com.yimin.model;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;

public class MergeOrder {

    private LinkedList<Order> orders = new LinkedList<>();

    //最后位置添加一个
    public void add(Order order) {
        orders.addLast(order);
    }

    // 从头获取数据
    public Order get() {
        return orders.get(0);
    }

    public int size() {
        return orders.size();
    }

    public BigDecimal getPrice() {
        return orders.get(0).getPrice();
    }

    public Iterator<Order> iterator() {
        return orders.iterator();
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = new BigDecimal(0);
        for (Order item : orders) {
            total = total.add(item.getAmount());
        }
        return total;
    }
}