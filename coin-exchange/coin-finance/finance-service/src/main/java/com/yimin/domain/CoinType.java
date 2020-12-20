package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
/**
    * 币种类型
    */
@ApiModel(value="com-yimin-domain-CoinType")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "coin_type")
public class CoinType {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 代码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="代码")
    @NotBlank
    private String code;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 状态：0-无效；1-有效；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：0-无效；1-有效；")
    @NotNull
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="更新时间")
    private Date lastUpdateTime;
}