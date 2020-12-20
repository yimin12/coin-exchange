package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
/**
    * 网站配置信息
    */
@ApiModel(value="com-yimin-domain-WebConfig")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "web_config")
public class WebConfig {
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="Id")
    private Long id;

    /**
     * 分组, LINK_BANNER ,WEB_BANNER
     */
    @TableField(value = "type")
    @ApiModelProperty(value="分组, LINK_BANNER ,WEB_BANNER")
    @NotBlank
    private String type;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    @NotBlank
    private String name;

    /**
     * 值
     */
    @TableField(value = "value")
    @ApiModelProperty(value="值")
    @NotBlank
    private String value;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Short sort;

    /**
     * 创建时间
     */
    @TableField(value = "created" ,fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 超链接地址
     */
    @TableField(value = "url")
    @ApiModelProperty(value="超链接地址")
    private String url;

    /**
     * 是否使用 0 否 1是
     */
    @TableField(value = "status")
    @ApiModelProperty(value="是否使用 0 否 1是")
    private Integer status;
}