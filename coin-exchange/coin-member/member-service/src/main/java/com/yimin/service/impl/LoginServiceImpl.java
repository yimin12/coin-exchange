package com.yimin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimin.feign.JwtToken;
import com.yimin.feign.OAuth2FeignClient;
import com.yimin.geetest.GeetestLib;
import com.yimin.model.LoginForm;
import com.yimin.model.LoginUser;
import com.yimin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 22:39
 *   @Description :
 *
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private StringRedisTemplate strRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GeetestLib geetestLib;

    /**
     * 会员的登录
     * @param loginForm
     * 登录的表单参数
     * @return
     * 登录的结果
     */
    @Override
    public LoginUser login(LoginForm loginForm) {
        log.info("用户{}开始登录,密码是{}", loginForm.getUsername(),loginForm.getPassword());
        checkFormData(loginForm);
        LoginUser loginUser = null;
        // 登录就是使用用户名和密码换一个token 而已--->远程调用->authorization-server
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", loginForm.getUsername(), loginForm.getPassword(), "member_type", basicToken);
        if (tokenResponseEntity.getStatusCode() == HttpStatus.OK) {
            JwtToken jwtToken = tokenResponseEntity.getBody();
            log.info("远程调用成功,结果为", JSON.toJSONString(jwtToken, true));
            // token 必须包含bearer
            loginUser = new LoginUser(loginForm.getUsername(), jwtToken.getExpiresIn(), jwtToken.getTokenType() + " " + jwtToken.getAccessToken(), jwtToken.getRefreshToken());
            // 使用网关解决登出的问题:
            // token 是直接存储的
            strRedisTemplate.opsForValue().set(jwtToken.getAccessToken(), "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
        }
        return loginUser;
    }

    /**
     * 校验数据
     * 极验证的数据校验
     *
     * @param loginForm
     */
    private void checkFormData(LoginForm loginForm){
        loginForm.check(geetestLib, redisTemplate);
    }
}
