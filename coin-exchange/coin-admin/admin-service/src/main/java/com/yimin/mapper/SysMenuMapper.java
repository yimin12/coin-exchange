package com.yimin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimin.domain.SysMenu;

import java.util.List;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    /**
     * 通过用户的id，查询用户的菜单数据
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}