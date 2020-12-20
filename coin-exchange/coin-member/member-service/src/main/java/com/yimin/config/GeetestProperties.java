package com.yimin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 20:05
 *   @Description :
 *
 */
@Data
@ConfigurationProperties(prefix = "geetest")
public class GeetestProperties {

    /**
     * 极验的ID
     */
    private String geetestId;

    /**
     * 极验的key
     */
    private String geetestKey;
}
