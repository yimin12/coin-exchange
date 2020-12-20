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
*   @Date : 2020/12/15 11:46
*   @Description : 
*
*/
    
/**
    * 实名认证信息
    */
@ApiModel(value="com-yimin-domain-UserAuthInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_auth_info")
public class UserAuthInfo {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 图片地址
     */
    @TableField(value = "image_url")
    @ApiModelProperty(value="图片地址")
    private String imageUrl;

    /**
     * 序号：1-身份证正面照；2-身份证反面照；3-手持身份证照片；
     */
    @TableField(value = "serialno")
    @ApiModelProperty(value="序号：1-身份证正面照；2-身份证反面照；3-手持身份证照片；")
    private Integer serialno;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="更新时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 用户每组审核记录唯一标识
     */
    @TableField(value = "auth_code")
    @ApiModelProperty(value="用户每组审核记录唯一标识")
    private Long authCode;
}