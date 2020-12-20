package com.yimin.rocket;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 20:14
 *   @Description :
 *
 */
public interface Sink {

    @Input("order_in")
    public MessageChannel messageChannel();

}
