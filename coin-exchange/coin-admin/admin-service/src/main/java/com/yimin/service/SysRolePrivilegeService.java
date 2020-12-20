package com.yimin.service;

import com.yimin.domain.SysMenu;
import com.yimin.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimin.model.RolePrivilegesParam;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysRolePrivilegeService extends IService<SysRolePrivilege>{

    /**
     * 查询角色的权限
     61
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuAndPrivileges(Long roleId);

    /**
     * 给角色授权权限
     * @param rolePrivilegesParam
     * @return
     */
    boolean grantPrivileges(RolePrivilegesParam rolePrivilegesParam);
}
