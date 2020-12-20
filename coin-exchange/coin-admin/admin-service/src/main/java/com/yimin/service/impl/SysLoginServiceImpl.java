package com.yimin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.yimin.domain.SysMenu;
import com.yimin.feign.JwtToken;
import com.yimin.feign.OAuth2FeignClient;
import com.yimin.model.LoginResult;
import com.yimin.service.SysLoginService;
import com.yimin.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 19:28
 *   @Description :
 *
 */
@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录的接口
     * @param username 登录用户名
     * @param password 登录密码
     * @return 登录的结果
     */
    @Override
    public LoginResult login(String username, String password) {
        log.info("用户{}开始登录", username);
        // 1 获取token，需要远程调用authorization-server
        ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        if(tokenResponseEntity.getStatusCode() != HttpStatus.OK){
            throw new ApiException(ApiErrorCode.FAILED);
        }
        JwtToken jwtToken = tokenResponseEntity.getBody();
        log.info("远程调用授权服务器成功，获取的token为{}", JSON.toJSONString(jwtToken,true));
        String token = jwtToken.getAccessToken();
        // 2 查询我们的菜单数据
        Jwt jwt = JwtHelper.decode(token);
        String jwtJsonStr = jwt.getClaims(); // get the json data from
        JSONObject jwtJson = JSON.parseObject(jwtJsonStr); // parse the json data to object
        Long userId = Long.valueOf(jwtJson.getString("user_name")); // get the userid from json obj
        List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);
        // 3 权限数据的查询 -- 不需要查询，应为jwt的信息中已经包含了权限信息
        JSONArray authoritiesJsonArray = jwtJson.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream() // 组装权限数据到一个List中
                .map(authorityJson-> new SimpleGrantedAuthority(authorityJson.toString())).collect(Collectors.toList());
        // 将该token存储在redis中，配置我们的网关做jwt登出操作，且我们返回给前端的数据缺少一个bearer：
        redisTemplate.opsForValue().set(token, "", jwtToken.getExpiresIn(),TimeUnit.SECONDS);
        return new LoginResult(jwtToken.getTokenType() + " " + token, menus, authorities);
    }
}
