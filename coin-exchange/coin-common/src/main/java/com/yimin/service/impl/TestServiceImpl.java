package com.yimin.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.yimin.model.WebLog;
import com.yimin.service.TestService;
import org.springframework.stereotype.Service;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 13:31
 *   @Description :
 *
 */
@Service
public class TestServiceImpl implements TestService {


    /**
     * 通过username来查询webLog日志
     * JetCache 缓存进入redis
     * CacheType.BOTH 同时也使用本地缓存
     * @param username
     * @return
     */
    @Cached(name = "com.yimin.service.impl.TestServiceImpl:",key = "#username", cacheType = CacheType.BOTH)
    @Override
    public WebLog get(String username) {
        WebLog webLog = new WebLog();
        webLog.setUsername(username);
        webLog.setResult("ok");
        return webLog;
    }
}
