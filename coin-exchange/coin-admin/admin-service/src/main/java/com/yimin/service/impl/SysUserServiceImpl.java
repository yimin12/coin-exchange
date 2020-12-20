package com.yimin.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.SysUserRole;
import com.yimin.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.SysUser;
import com.yimin.mapper.SysUserMapper;
import com.yimin.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Autowired
    private SysUserRoleService sysUserRoleService;

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
    @Override
    public Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname) {
        Page<SysUser> pageData = page(page,
                new LambdaQueryWrapper<SysUser>()
                        .like(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
                        .like(!StringUtils.isEmpty(fullname), SysUser::getFullname, fullname)
        );
        List<SysUser> records = pageData.getRecords();
        if(!CollectionUtils.isEmpty(records)){
            for (SysUser record : records) {
                List<SysUserRole> userRoles = sysUserRoleService.list(new
                        LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId()));
                if(!CollectionUtils.isEmpty(userRoles)){
                    record.setRole_strings(
                            userRoles.stream().
                                    map(sysUserRole -> sysUserRole.getRoleId().toString())
                                    .collect(Collectors.joining(",")));
                }
            }
        }
        return pageData;
    }


    /**
     * 新增员工
     * @param sysUser
     * @return
     */
    @Override
    @Transactional // 当你想合并多个写操作成为一个原子操作的时候添加 Transactional 注解
    public boolean addUser(SysUser sysUser) {
        // 1. 获取用户的密码
        String password = sysUser.getPassword();
        // 2 获取用户角色的Ids
        String role_strings = sysUser.getRole_strings();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        sysUser.setPassword(encode);
        boolean save = super.save(sysUser); // 第一个写操作
        if(save){
            // 如果保存成功了
            if(!StringUtils.isEmpty(role_strings)){
                String[] roleIds =role_strings.split(",");
                List<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length);
                for(String roleId: roleIds){
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRoleList.add(sysUserRole);
                }
                sysUserRoleService.saveBatch(sysUserRoleList); // 第二个写操作，多个写操作要开启事务
            }
        }
        return save;
    }

    /**
     * 更新员工权限和相关信息
     * @param sysUser
     * @return
     */
    @Override
    @Transactional
    public boolean updateUser(SysUser sysUser) {
        String role_strings = sysUser.getRole_strings();
        boolean flag = super.updateById(sysUser);
        if(flag){
            if(!StringUtils.isEmpty(role_strings)){
                String[] roleIds = role_strings.split(",");
                List<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length);
                for(String roleId:roleIds){
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRoleList.add(sysUserRole);
                }
                sysUserRoleService.saveBatch(sysUserRoleList);
            }
        }
        return flag;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean flag = super.removeByIds(idList);
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, idList));
        return flag;
    }
}
