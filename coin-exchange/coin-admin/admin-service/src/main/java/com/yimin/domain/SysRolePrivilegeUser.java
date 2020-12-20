package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
/**
    * 用户权限配置
    */
@ApiModel(value="com-yimin-domain-SysRolePrivilegeUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_privilege_user")
public class SysRolePrivilegeUser {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 角色Id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="角色Id")
    private Long roleId;

    /**
     * 用户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户Id")
    private Long userId;

    /**
     * 权限Id
     */
    @TableField(value = "privilege_id")
    @ApiModelProperty(value="权限Id")
    private Long privilegeId;
}