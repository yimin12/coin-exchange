package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysUserService extends IService<SysUser>{


    /**
     * 分页查询员工
     * @param page
     * 分页参数
     * @param mobile
     * 员工的手机号
     * @param fullname
     * 员工的全名称
     * @return
     */
    Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname);

    /**
     * 新增员工
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);

    /**
     * 更新员工权限和相关信息
     * @param sysUser
     * @return
     */
    boolean updateUser(SysUser sysUser);
}
