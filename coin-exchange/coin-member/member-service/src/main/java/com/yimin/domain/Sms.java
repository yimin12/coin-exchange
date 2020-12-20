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
*   @Date : 2020/12/15 11:49
*   @Description : 
*
*/
    
/**
    * 短信信息
    */
@ApiModel(value="com-yimin-domain-Sms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sms")
public class Sms {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
     * 短信模板ID
     */
    @TableField(value = "template_code")
    @ApiModelProperty(value="短信模板ID")
    @NotBlank
    private String templateCode;

    /**
     * 国际区号
     */
    @TableField(value = "country_code")
    @ApiModelProperty(value="国际区号")
    @NotNull
    private String countryCode;

    /**
     * 短信接收手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="短信接收手机号")
    @NotBlank
    private String mobile;

    /**
     * 短信内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="短信内容")
    private String content;

    /**
     * 短信状态：0，默认值；大于0，成功发送短信数量；小于0，异常；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="短信状态：0，默认值；大于0，成功发送短信数量；小于0，异常；")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 发送时间
     */
    @TableField(value = "last_update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="发送时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;
}