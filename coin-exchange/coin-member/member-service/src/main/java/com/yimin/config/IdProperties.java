package com.yimin.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/16 14:33
 *   @Description :
 *
 */
@ConfigurationProperties(prefix = "identify")
@Data
public class IdProperties {

    /**
     * 对应你购买的AppKey  https://idcert.market.alicloudapi.com/idcard?idCard=%s&name=%s
     */
    private String appKey;

    /**
     * 对应你狗购买的appSecret
     */
    private String appSecret;

    /**
     * 对应你购买的appCode
     */
    private String appCode;

    /**
     * 对应Url的地址
     */
    private String url;
}
