package com.yimin.service.impl;

import com.yimin.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.SysMenuMapper;
import com.yimin.domain.SysMenu;
import com.yimin.service.SysMenuService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 通过用户的id来查询用户的菜单数据
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {

        // 如果是超级用户，则直接给予全部权限
        if(sysRoleService.isSuperAdmin(userId)){
            return list();
        }

        // 如果该用户是普通用户->>查询该用户的角色 --> 查询菜单
        return sysMenuMapper.selectMenusByUserId(userId);
    }
}
