package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.UserAuthAuditRecordMapper;
import com.yimin.domain.UserAuthAuditRecord;
import com.yimin.service.UserAuthAuditRecordService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
@Service
public class UserAuthAuditRecordServiceImpl extends ServiceImpl<UserAuthAuditRecordMapper, UserAuthAuditRecord> implements UserAuthAuditRecordService{


    /**
     * 获取一个用户的审核记录
     *
     * @param id
     * @return
     */
    @Override
    public List<UserAuthAuditRecord> getUserAuthAuditRecordList(Long id) {
        return list(new LambdaQueryWrapper<UserAuthAuditRecord>().eq(UserAuthAuditRecord::getUserId, id)
                .orderByDesc(UserAuthAuditRecord::getCreated).last("limit 3"));
    }

}
