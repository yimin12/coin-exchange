package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;



/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
/**
    * 系统资讯公告信息
    */
@ApiModel(value="com-yimin-domain-Notice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "notice")
public class Notice {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="标题")
    @NotBlank
    private String title;

    /**
     * 简介
     */
    @TableField(value = "description")
    @ApiModelProperty(value="简介")
    @NotBlank
    private String description;

    /**
     * 作者
     */
    @TableField(value = "author")
    @ApiModelProperty(value="作者")
    @NotBlank
    private String author;

    /**
     * 文章状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="文章状态")
    private Integer status;

    /**
     * 文章排序，越大越靠前
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="文章排序，越大越靠前")
    @NotNull
    private Integer sort;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="内容")
    @NotBlank
    private String content;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="最后修改时间")
    private Date lastUpdateTime;

    /**
     * 创建日期
     */
    @TableField(value = "created" ,fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建日期")
    private Date created;
}