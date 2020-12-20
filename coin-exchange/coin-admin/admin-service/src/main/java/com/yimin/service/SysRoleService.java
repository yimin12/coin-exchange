package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysRoleService extends IService<SysRole>{

    /**
     * 判断一个用户是否为超级用户
     * @param userId
     * @return
     */
    boolean isSuperAdmin(Long userId);

    /**
     * 使用角色的名称模糊分页角色查询
     * @param page
     * @param name
     * @return
     */
    Page<SysRole> findByPage(Page<SysRole> page, String name);
}
