package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    * 系统日志
    */
@ApiModel(value="com-yimin-domain-SysUserLog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user_log")
public class SysUserLog {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 组
     */
    @TableField(value = "`group`")
    @ApiModelProperty(value="组")
    private String group;

    /**
     * 用户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户Id")
    private Long userId;

    /**
     * 日志类型 1查询 2修改 3新增 4删除 5导出 6审核
     */
    @TableField(value = "type")
    @ApiModelProperty(value="日志类型 1查询 2修改 3新增 4删除 5导出 6审核")
    private Short type;

    /**
     * 方法
     */
    @TableField(value = "method")
    @ApiModelProperty(value="方法")
    private String method;

    /**
     * 参数
     */
    @TableField(value = "params")
    @ApiModelProperty(value="参数")
    private String params;

    /**
     * 时间
     */
    @TableField(value = "time")
    @ApiModelProperty(value="时间")
    private Long time;

    /**
     * IP地址
     */
    @TableField(value = "ip")
    @ApiModelProperty(value="IP地址")
    private String ip;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;
}