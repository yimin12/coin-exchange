package com.yimin.rocket;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/20 12:13
 *   @Description :
 *
 */
public interface Sink {

    @Input("tio_group")
    MessageChannel messageGroupChannel() ;

}
