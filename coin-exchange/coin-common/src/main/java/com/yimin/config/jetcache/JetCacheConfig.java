package com.yimin.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;
/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 20:31
 *   @Description :
 *
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.yimin.service.impl")
public class JetCacheConfig {

}
