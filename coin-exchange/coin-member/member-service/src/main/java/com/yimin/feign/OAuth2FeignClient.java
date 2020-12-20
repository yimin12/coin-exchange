package com.yimin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 19:51
 *   @Description :
 *
 */
@FeignClient(value = "authorization-server")
public interface OAuth2FeignClient {

    /**
     * 所有的参数在postman请求过程中都有对应关系，其中header中对应的时Authorization的coin-api和coin-secret -> basic token
     * @param grantType
     * @param username
     * @param password
     * @param loginType
     * @return
     */
    @PostMapping("/oauth/token")
    ResponseEntity<JwtToken> getToken(
            @RequestParam("grant_type") String grantType, // 授权类型
            @RequestParam("username") String username, // 用户名
            @RequestParam("password") String password, // 用户名密码
            @RequestParam("login_type") String loginType, // 登录类型
            @RequestHeader("Authorization") String basicToken // 由第三方加密进来的值并且封装在请求头中
    );
}
