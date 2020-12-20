package com.yimin.service;

import com.yimin.domain.UserAuthAuditRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
public interface UserAuthAuditRecordService extends IService<UserAuthAuditRecord>{


    /**
     * 获取一个用户的审核记录
     *
     * @param id
     * @return
     */
    List<UserAuthAuditRecord> getUserAuthAuditRecordList(Long id);
    
}
