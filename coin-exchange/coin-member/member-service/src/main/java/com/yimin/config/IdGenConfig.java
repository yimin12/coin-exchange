package com.yimin.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/16 17:21
 *   @Description :
 *      id生成器
 */
@Configuration
public class IdGenConfig {

    @Value("${id.machine:0}")
    private int machineCode;

    @Value("${id.app:0}")
    private int appCode;

    @Bean
    public Snowflake snowflake(){
        Snowflake snowflake = new Snowflake(appCode, machineCode);
        return snowflake;

    }
}
