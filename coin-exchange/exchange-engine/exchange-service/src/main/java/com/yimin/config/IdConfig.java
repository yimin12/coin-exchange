package com.yimin.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {

    @Value("${snow.app.id:3}")
    private Integer appId ;

    @Value("${snow.data.id:1}")
    private Integer dataId ;
    /**
     * 雪花算法
     * @return
     */
    @Bean
    public Snowflake snowflake(){
        Snowflake snowflake = new Snowflake(appId,dataId);
        return snowflake ;
    }
}
