package com.yimin.rocket;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/20 12:14
 *   @Description :
 *
 */
@Configuration
@EnableBinding(value = Sink.class)
public class RocketConfig {
}
