package com.yimin.controller;

import com.yimin.domain.SysMenu;
import com.yimin.model.R;
import com.yimin.model.RolePrivilegesParam;
import com.yimin.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 0:02
 *   @Description :
 *
 */
@Api(tags = "角色权限配置")
@RestController
public class SysRolePrivilegeController {

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @GetMapping("/roles_privileges")
    @ApiOperation(value = "查询角色的权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId" ,value = "角色的ID")
    })
    public R<List<SysMenu>> findSysMenuAndPrivileges(Long roleId){
        List<SysMenu> sysMenus =
                sysRolePrivilegeService.findSysMenuAndPrivileges(roleId) ;
        return R.ok(sysMenus) ;
    }

    /**
     * 给某个用户赋予权限
     */
    @GetMapping("/grant_privileges")
    @ApiOperation(value = "授予角色某种权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolePrivilegesParam", value = "rolePrivilegesParam json type")
    })
    public R grantPrivileges(@RequestBody RolePrivilegesParam rolePrivilegesParam){
        boolean flag = sysRolePrivilegeService.grantPrivileges(rolePrivilegesParam);
        if(flag){
            return R.ok();
        }
        return R.fail("授予失败") ;
    }

}
