package com.yimin.config;

import com.yimin.geetest.GeetestLib;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 20:08
 *   @Description :
 *
 */
@Configuration
@EnableConfigurationProperties(GeetestProperties.class)
public class GeetestAutoConfiguration {


    private GeetestProperties geetestProperties;

    public GeetestAutoConfiguration(GeetestProperties geetestProperties){
        this.geetestProperties = geetestProperties;
    }

    @Bean
    public GeetestLib geetestLib(){
        GeetestLib geetestLib = new GeetestLib(geetestProperties.getGeetestId(), geetestProperties.getGeetestKey());
        return geetestLib;
    }
}
