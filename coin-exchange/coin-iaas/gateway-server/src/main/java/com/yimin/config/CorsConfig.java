package com.yimin.config;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 21:16
 *   @Description :
 *      如果前端没有解决跨域问题，后端也是可以添加配置类来解决跨域问题
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*"); // 允许跨域访问方法
        config.addAllowedOrigin("*"); // 允许跨域访问的域
        config.addAllowedHeader("*"); // 允许跨域的头
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config); // 所有路径都支持跨域
        return new CorsWebFilter(source);
    }
}
