package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
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
    
@ApiModel(value="com-yimin-domain-UserAccountFreeze")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_account_freeze")
public class UserAccountFreeze {
    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Long userId;

    @TableField(value = "freeze")
    @ApiModelProperty(value="")
    private BigDecimal freeze;
}