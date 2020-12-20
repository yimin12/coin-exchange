package com.yimin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 10:58
 *   @Description :
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MemberServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(MemberServiceApplication.class, args);
    }
}
