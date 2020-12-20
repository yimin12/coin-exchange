package com.yimin.config.rocket;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 22:48
 *   @Description :
 *
 */
@Configuration
@EnableBinding(value = Source.class)
public class RocketMQConfig {
}
