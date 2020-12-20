package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.SysPrivilegeMapper;
import com.yimin.domain.SysPrivilege;
import com.yimin.service.SysPrivilegeService;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService{

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    /**
     * 获取该菜单下所有权限
     * @param menuId 当前菜单的id
     * @param roleId 代表当前查询角色的id
     * @return
     */
    @Override
    public List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId) {
        // 1 查询所有的该菜单下的权限
        List<SysPrivilege> sysPrivileges = list(new
                LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
        if(CollectionUtils.isEmpty(sysPrivileges)){
            return Collections.emptyList() ;
        }
        // 2 当前传递的角色使用包含该权限信息也要放进去
        for (SysPrivilege sysPrivilege : sysPrivileges) {
            Set<Long> currentRoleSysPrivilegeIds =
                    sysPrivilegeMapper.getPrivilegesByRoleId(roleId) ;
            if (currentRoleSysPrivilegeIds.contains(sysPrivilege.getId())){
                sysPrivilege.setOwn(1); // 当前的角色是否有该权限
            }
        }
        return sysPrivileges;
    }
}
