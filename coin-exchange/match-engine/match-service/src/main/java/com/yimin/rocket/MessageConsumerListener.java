package com.yimin.rocket;

import com.yimin.disruptor.DisruptorTemplate;
import com.yimin.domain.EntrustOrder;
import com.yimin.model.Order;
import com.yimin.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 20:30
 *   @Description :
 *
 */
@Service
@Slf4j
public class MessageConsumerListener {

    @Autowired
    private DisruptorTemplate disruptorTemplate;


    @StreamListener("order_in")
    public void handleMessage(EntrustOrder entrustOrder){
        Order order = null;
        if(entrustOrder.getStatus() == 2){
            // 取消订单
            order = new Order();
            order.setOrderId(entrustOrder.getId().toString());

        } else {
            order = BeanUtils.entrustOrder2Order(entrustOrder);
        }
        log.info("接收到了委托单:{}", order);
        disruptorTemplate.onData(order);
    }
}
