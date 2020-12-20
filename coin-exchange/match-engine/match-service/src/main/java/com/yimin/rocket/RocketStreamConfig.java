package com.yimin.rocket;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 20:32
 *   @Description :
 *
 */
@Configuration
@EnableBinding(value = {Sink.class, Source.class})
public class RocketStreamConfig {

    /**
     * 开启我们的stream的开发
     */
}
