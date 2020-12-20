package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.dto.UserDto;
import com.yimin.feign.UserServiceFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.WorkIssue;
import com.yimin.mapper.WorkIssueMapper;
import com.yimin.service.WorkIssueService;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

    @Autowired
    private UserServiceFeign userServiceFeign;

    /**
     * 条件分页查询工单列表
     * @param page
     * 分页参数
     * @param status
     * 工单的状态
     * @param startTime
     * 查询的工单创建起始时间
     * @param endTime
     * 查询的工单创建截至时间
     * @return
     */
    @Override
    public Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime) {
        Page<WorkIssue> pageData = page(page, new LambdaQueryWrapper<WorkIssue>().eq(status != null, WorkIssue::getStatus, status)
                .between(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime), WorkIssue::getCreated, startTime, endTime + " 23:59:59"));
        List<WorkIssue> records = pageData.getRecords();
        if(CollectionUtils.isEmpty(records)){
            return pageData;
        }
        // 如果工单里面有数据的话，向member-service调用rpc远程调用
        // 1. 收集Id， 一次性发送
//        for(WorkIssue record:records){
//            Long userId = record.getUserId();
//            // 每次每个id都调用一次rpc，花费是十分昂贵的。所以这个是错误示范
//        }
        List<Long> userIds = records.stream().map(WorkIssue::getUserId).collect(Collectors.toList()); // stream底层也是使用iterator来实现的
        // 2.远程调用
        Map<Long, UserDto> basicUsers = userServiceFeign.getBasicUsers(userIds, null, null);
        if(CollectionUtils.isEmpty(basicUsers)){
            return pageData;
        }
        // 2.1 小技巧 list如果用增强循环，没办法使用下标定位元素，将他转化成map来直接调用key就能解决
        records.forEach(workIssue -> {
            // 给每个workIssue设置用户的信息
            UserDto userDto = basicUsers.get(workIssue.getUserId());
            workIssue.setUsername(userDto == null ? "测试用户":userDto.getUsername());
            workIssue.setRealName(userDto == null ? "测试用户":userDto.getRealName());
        });
        return pageData;

    }

    /**
     * 前台系统查询客户工单
     *
     * @param page
     * @return
     */
    @Override
    public Page<WorkIssue> getIssueList(Page<WorkIssue> page,Long userId) {
        return page(page,new LambdaQueryWrapper<WorkIssue>()
                        .eq(WorkIssue::getUserId,userId)
                                            .eq(WorkIssue::getStatus,1)
        );
    }
}
