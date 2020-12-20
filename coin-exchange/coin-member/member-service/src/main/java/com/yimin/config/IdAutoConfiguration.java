package com.yimin.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/16 14:44
 *   @Description :
 *
 */
@Configuration
@EnableConfigurationProperties(IdProperties.class)
public class IdAutoConfiguration {

    private static IdProperties idProperties;

    /**
     * 发请求的工具
     */
    private static RestTemplate restTemplate = new RestTemplate(); // Spring框架提供的一个实时发送请求的工作

    public IdAutoConfiguration(IdProperties idProperties){
        IdAutoConfiguration.idProperties = idProperties;
    }

    /**
     * 用户信息的实名认证
     * @param realName
     * 用户的真实信息
     * @param cardNum
     * 用户的身份证号
     * @return
     * 验证的结果
     */
    public static boolean check(String realName, String cardNum){
        /**
         * 本次请求我们是AppCode 的形式验证: Authorization:APPCODE 你自己的AppCode
         * -H Authorization:APPCODE 你自己的AppCode
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","APPCODE " + idProperties.getAppCode());
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                //%s 是变量,
                String.format(idProperties.getUrl(), cardNum, realName),
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
        // https://market.aliyun.com/products/57000002/cmapi022049.html?spm=5176.2020520132.101.2.2fe57218VVSjB0#sku=yuncode1604900000
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            String body = responseEntity.getBody();
            JSONObject jsonObject = JSON.parseObject(body);
            String status = jsonObject.getString("status");
            if("01".equals(status)){
                return true;
            }
        }
        return false;
    }
}
