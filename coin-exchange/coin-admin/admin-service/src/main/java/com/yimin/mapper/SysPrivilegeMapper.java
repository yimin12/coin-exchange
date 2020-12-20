package com.yimin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimin.domain.SysPrivilege;

import java.util.List;
import java.util.Set;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    /**
     * 使用角色Id 查询权限
     * @param roleId
     * @return
     */
    List<SysPrivilege> selectByRoleId(Long roleId);

    /**
     * 使用角色的ID 查询权限的id
     * @param roleId
     * @return
     */
    Set<Long> getPrivilegesByRoleId(Long roleId);
}