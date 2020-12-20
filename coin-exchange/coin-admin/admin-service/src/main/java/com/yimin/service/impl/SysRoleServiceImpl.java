package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.SysRole;
import com.yimin.mapper.SysRoleMapper;
import com.yimin.service.SysRoleService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 判断一个用户是否为超级用户
     * @param userId
     * @return
     */
    @Override
    public boolean isSuperAdmin(Long userId) {
        // 当用户的Role code为ROLE_ADMIN时，给用户为超级管理员
        // 用户id -> 用户的角色 -> 该角色的Code是否为ROLE_ADMIN
        String roleCode = sysRoleMapper.getUserRoleCode(userId);
        if(!StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN")){
            return true;
        }
        return false;
    }

    /**
     * 使用角色的名称模糊分页角色查询
     * @param page
     * @param name
     * @return
     */
    @Override
    public Page<SysRole> findByPage(Page<SysRole> page, String name) {
        return page(page, new LambdaQueryWrapper<SysRole>().like(
                !StringUtils.isEmpty(name),SysRole::getName, name
        ));
    }
}
