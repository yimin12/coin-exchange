package com.yimin.disruptor;

import lombok.Data;

import java.io.Serializable;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 17:21
 *   @Description :
 *
 */
@Data
public class OrderEvent implements Serializable {

    /**
     * 时间戳
     */
    private final long timestamp;

    /**
     * 事件携带的数据
     */
    protected transient Object source;

    public OrderEvent(){
        this.timestamp = System.currentTimeMillis();
    }

    public OrderEvent(Object source){
        this.timestamp = System.currentTimeMillis();
        this.source = source;
    }
}
