package com.yimin.config.feign;

import com.google.common.net.HttpHeaders;
import com.yimin.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 11:37
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
        // 1 我们可以从request的上下文环境里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String header = null ;
        if (requestAttributes == null) {
//            log.info("没有请求的上下文,故无法进行token的传递");
            header = "bearer "+ Constants.INSIDE_TOKEN ;
        }else{
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            header = request.getHeader(HttpHeaders.AUTHORIZATION); // 获取我们请求上下文的头里面的AUTHORIZATION
        }

        if (!StringUtils.isEmpty(header)) {
            template.header(HttpHeaders.AUTHORIZATION, header);
//            log.info("本次token传递成功,token的值为:{}", header);
        }
    }
}
