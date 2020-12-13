package com.yimin.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.yimin.model.WebLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 21:42
 *   @Description :
 *
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class WebLogAspect {

    /**
     * 日志记录：
     *  环绕通知：方法执行之前、之后
     */

    /**
     * 1 定义切入点
     */
    @Pointcut("execution(* com.yimin.controller.*.*(..))") // controller 包里面所有类，类里面的所有方法 都有该切面
    public void webLog(){}


    /**
     * 2 记录日志的环绕通知
     */
    @Around("webLog()")
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object result = null;
        WebLog webLog = new WebLog();
        Long start = System.currentTimeMillis();

        // 执行该方法的真实调用
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();
        webLog.setSpendTime((int)(start - end) / 1000); // 请求该接口花费的时间
        // 获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取安全的上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String url = request.getRequestURL().toString();
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(url);
        webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath())); // http://ip:port/
        webLog.setUsername(authentication == null ? "anonymous" : authentication.getPrincipal().toString()); // 获取用户id
        webLog.setIp(request.getRemoteAddr());

        // 获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        Method method = signature.getMethod();
        // 我们会使用swagger工具，所以必须在上述使用的方法之前添加@ApiOperation(value="")注释，swagger才能识别
        // 获取ApiOperation
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        webLog.setDescription(annotation==null ? "no desc" : annotation.value());
        webLog.setMethod(targetClassName+ "." + method.getName()); // com.yimin.controller.UserController.login()
        webLog.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs())); // {"key_参数名称:value_参数的值"}
        webLog.setResult(result);
        log.info(JSON.toJSONString(webLog, true));
        return request;
    }

    /**
     * 获取方法的执行参数
     * @param method
     * @param args
     * @return
     * {"key_参数的名称":"value_参数的值"}
     */
    private Object getMethodParameter(Method method, Object[] args) {
        Map<String, Object> methodParametersWithValues = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i <parameterNames.length ; i++) {
            if(parameterNames[i].equals("password") || parameterNames[i].equals("file")){
                methodParametersWithValues.put(parameterNames[i],"受限的支持类型") ;
            }else{
                methodParametersWithValues.put(parameterNames[i],args[i]) ;
            }

        }
        return methodParametersWithValues ;
    }
}
