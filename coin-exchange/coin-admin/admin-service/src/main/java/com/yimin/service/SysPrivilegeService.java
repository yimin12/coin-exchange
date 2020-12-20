package com.yimin.service;

import com.yimin.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysPrivilegeService extends IService<SysPrivilege>{


    /**
     * 获取该菜单下所有权限
     * @param menuId 当前菜单的id
     * @param roleId 代表当前查询角色的id
     * @return
     */
    List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId);
}
