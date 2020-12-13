package com.yimin.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 20:14
 *   @Description :
 *
 */
@Slf4j
public class OAuth2FeignConfig implements RequestInterceptor {

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        // Get the token from the request in the context
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            log.info("没有请求的上下文，故无法进行token的传递");
        }
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION); // 获取上下文中请求头中的AUTHORIZATION
        if(!StringUtils.isEmpty(header)){
            template.header(HttpHeaders.AUTHORIZATION, header);
            log.info("本次token传递成功，token的值为:{}", header);
        }

    }
}
