package com.yimin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimin.domain.SysRole;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 获取角色Code的实现
     * @param userId
     * @return
     */
    String getUserRoleCode(Long userId);
}