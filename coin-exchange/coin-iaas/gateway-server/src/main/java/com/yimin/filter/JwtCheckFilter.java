package com.yimin.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 13:43
 *   @Description :
 *      Capture the jwt info in the gateway server and store it in redis
 */
@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${no.require.urls:/admin/login}")
    private Set<String> noRequireTokenUris;

    /**
     * 过滤拦截器到用户的之后做什么
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1 : 该接口是否需要token 才能访问
        if(!isRequireToken(exchange)){
            return chain.filter(exchange) ;// 不需要token ,直接放行
        }
        // 2: 取出用户的token
        String token = getUserToken(exchange) ;
        // 3 判断用户的token 是否有效
        if(StringUtils.isEmpty(token)){
            return buildNoAuthorizationResult(exchange) ;
        }
        Boolean hasKey = redisTemplate.hasKey(token);
        if(hasKey!=null && hasKey){
            return chain.filter(exchange) ;// token有效 ,直接放行
        }
        return buildNoAuthorizationResult(exchange) ;
    }

    /**
     * 给用户响应一个没有token的错误，error exception
     * @param exchange
     * @return
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type","application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED) ;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error","NoAuthorization") ;
        jsonObject.put("errorMsg","Token is Null or Error") ;
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap)) ;
    }


    /**
     * 返回请求中的token信息（从请求头中）
     * @param exchange
     * @return
     */
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token ==null ? null : token.replace("bearer ","") ;
    }

    /**
     * 判断接口是否需要token
     * @param exchange
     * @return
     */
    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if(noRequireTokenUris.contains(path)){
            return false ; // 不需要token
        }
        return Boolean.TRUE ;
    }

    /**
     * 主要的拦截顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
