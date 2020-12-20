package com.yimin.aspect;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.yimin.domain.SysUserLog;
import com.yimin.model.WebLog;
import com.yimin.service.SysUserLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 17:16
 *   @Description :
 *      用户在登录的时候将操作记录录入到日志中
 */
@Aspect
@Order(2)
@Slf4j
public class WebLogAdminAspect {

    /**
     * 雪花算法
     * 1 : 机器的id
     * 2 : 应用的id
     */
    private Snowflake snowflake = new Snowflake(1,1) ;
    @Autowired
    private SysUserLogService sysUserLogService ;
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
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null ;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis() ;

        // 执行方法的真实调用
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

        long end = System.currentTimeMillis() ;


        webLog.setSpendTime((int)(start-end)/1000); // 请求该接口花费的时间
        // 获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取安全的上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String url = request.getRequestURL().toString();
        webLog.setUri(request.getRequestURI()); // 设置请求的uri
        webLog.setUrl(url);
        webLog.setBasePath(StrUtil.removeSuffix(url, URLUtil.url(url).getPath())); // http://ip:port/
        webLog.setUsername(authentication==null ? "anonymous":authentication.getPrincipal().toString()); // 获取用户的id
        webLog.setIp(request.getRemoteAddr()); // TODO 获取ip 地址


        // 获取方法
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        // 获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        Method method = signature.getMethod();
        // 因为我们会使用Swagger 这工具，我们必须在方法上面添加@ApiOperation(value="")该注解
        // 获取ApiOperation
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        webLog.setDescription(annotation==null ? "no desc":annotation.value());
        webLog.setMethod(targetClassName+"."+method.getName()); // com.bjsxt.controller.UserController.login()
        webLog.setParameter(getMethodParameter(method,proceedingJoinPoint.getArgs())); //{"key_参数的名称":"value_参数的值"}
        webLog.setResult(result);

        SysUserLog sysUserLog = new SysUserLog();

        sysUserLog.setId(snowflake.nextId());
        sysUserLog.setCreated(new Date());
        sysUserLog.setDescription(webLog.getDescription());
        sysUserLog.setGroup(webLog.getDescription());
        sysUserLog.setUserId(Long.valueOf(webLog.getUsername()));
        sysUserLog.setMethod(webLog.getMethod());
        sysUserLog.setIp(sysUserLog.getIp());
        sysUserLogService.save(sysUserLog) ;
        return result ;
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
            // MultipartFile 使用fastjson 序列化会报错，因此我们需要忽略掉它：
            if(parameterNames[i].equals("password") || parameterNames[i].equals("file")){
                methodParametersWithValues.put(parameterNames[i],"受限的支持类型") ;
            }else{
                methodParametersWithValues.put(parameterNames[i],args[i]) ;
            }

        }

        return methodParametersWithValues ;
    }
}
