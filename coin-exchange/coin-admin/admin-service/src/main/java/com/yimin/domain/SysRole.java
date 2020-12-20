package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
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
    * 角色
    */
@ApiModel(value="com-yimin-domain-SysRole")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
public class SysRole {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 代码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="代码")
    private String code;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 创建人
     */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by",fill = FieldFill.UPDATE)
    @ApiModelProperty(value="修改人")
    private Long modifyBy;

    /**
     * 状态0:禁用 1:启用
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态0:禁用 1:启用")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;
}