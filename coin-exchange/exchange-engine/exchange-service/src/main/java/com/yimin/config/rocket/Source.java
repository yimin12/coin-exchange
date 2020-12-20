package com.yimin.config.rocket;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 22:46
 *   @Description :
 *
 */
public interface Source {

    @Output("order_out")
    MessageChannel outputMessage() ;
}
