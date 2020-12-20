package com.yimin.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 19:41
 *   @Description :
 *
 */
@Data
public class JwtToken {

    /**
     * authorization server 回复的token，用于登录
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token的类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 过期时间
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    /**
     * token的时间按范围
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * 证书
     */
    @JsonProperty("jti")
    private String jti;
}
