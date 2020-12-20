package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
/**
    * 系统菜单
    */
@ApiModel(value="com-yimin-domain-SysMenu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class SysMenu {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 上级菜单ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value="上级菜单ID")
    private Long parentId;

    /**
     * 上级菜单唯一KEY值
     */
    @TableField(value = "parent_key")
    @ApiModelProperty(value="上级菜单唯一KEY值")
    private String parentKey;

    /**
     * 类型 1-分类 2-节点
     */
    @TableField(value = "type")
    @ApiModelProperty(value="类型 1-分类 2-节点")
    private Byte type;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value="描述")
    private String desc;

    /**
     * 目标地址
     */
    @TableField(value = "target_url")
    @ApiModelProperty(value="目标地址")
    private String targetUrl;

    /**
     * 排序索引
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序索引")
    private Integer sort;

    /**
     * 状态 0-无效； 1-有效；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态 0-无效； 1-有效；")
    private Byte status;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by")
    @ApiModelProperty(value="修改人")
    private Long modifyBy;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 一个菜单对应多个权限
     */
    @TableField(exist = false)
    @ApiModelProperty("该菜单下的所有的权限")
    private List<SysPrivilege> sysPrivileges = Collections.emptyList();

    /**
     * 一个菜单对应多个子菜单
     */
    @TableField(exist = false)
    @ApiModelProperty("该菜单的子菜单")
    private List<SysMenu> childs = Collections.emptyList();

    /**
     * 与前端VUE配对的唯一键值key
     */
    @TableField(exist = false)
    @ApiModelProperty("该菜单的唯一Key 值")
    private String menuKey;

    /**
     * 获取菜单的唯一Key 凭证
     * @return
     */
    public String getMenuKey() {
        if (!StringUtils.isEmpty(parentKey)) {
            return parentKey+"."+id;
        }else {
            return id.toString();
        }
    }
}