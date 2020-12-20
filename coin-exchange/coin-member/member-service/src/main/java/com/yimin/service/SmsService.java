package com.yimin.service;

import com.yimin.domain.Sms;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:49
*   @Description : 
*
*/
    
public interface SmsService extends IService<Sms>{


    /**
     * 短信的发现
     * @param sms
     * 短信
     * @return
     * 是否发送成功
     */
    boolean sendSms(Sms sms);
}
