package com.yimin.aspect;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.yimin.model.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 21:36
 *   @Description :
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1 内部API调用的异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public R handlerApiException(ApiException exception){
        IErrorCode errorCode = exception.getErrorCode();
        if(errorCode != null){
            return R.fail(errorCode);
        }
        return R.fail(exception.getMessage());
    }

    /**
     * 2 方法参数校验失败的异常
     * MethodArgumentNotValidException
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if(fieldError != null){
                return R.fail(fieldError.getField() + fieldError.getDefaultMessage());
            }
        }
        return R.fail(exception.getMessage());
    }

    /**
     * 3 对象内部使用Validate 没有校验成功的异常
     */
    public R handlerBindException(BindException exception){
        BindingResult bindingResult = exception.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            if(fieldError != null){
                return R.fail(fieldError.getField() + fieldError.getDefaultMessage());
            }
        }
        return R.fail(exception.getMessage());
    }

}
