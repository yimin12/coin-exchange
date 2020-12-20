package com.yimin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 16:18
 *   @Description :
 *
 */
@Data
@ApiModel(value = "接受角色和权限数据")
public class RolePrivilegesParam {

    @ApiModelProperty(value = "角色的ID")
    private Long roleId;

    @ApiModelProperty(value = "角色的包含权限")
    private List<Long> privilegeIds = Collections.emptyList();
}
