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
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
/**
    * 创新交易币种表
    */
@ApiModel(value="com-yimin-domain-ForexCoin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "forex_coin")
public class ForexCoin {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="")
    private Long id;

    /**
     * 币种名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="币种名称")
    private String name;

    /**
     * 币种标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="币种标题")
    private String title;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Byte sort;

    /**
     * 状态: 0禁用 1启用
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态: 0禁用 1启用")
    private Boolean status;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;
}