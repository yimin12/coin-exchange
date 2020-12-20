package com.yimin.service;

import com.yimin.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysMenuService extends IService<SysMenu>{


    /**
     * 通过用户的id来查询用户的菜单数据
     * @param userId
     * @return
     */
    List<SysMenu> getMenusByUserId(Long userId);
}
