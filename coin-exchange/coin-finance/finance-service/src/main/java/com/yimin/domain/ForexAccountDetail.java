package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
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
    * 持仓账户流水
    */
@ApiModel(value="com-yimin-domain-ForexAccountDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "forex_account_detail")
public class ForexAccountDetail {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 持仓账户ID
     */
    @TableField(value = "account_id")
    @ApiModelProperty(value="持仓账户ID")
    private Long accountId;

    /**
     * 收支类型：开仓；2-平仓；
     */
    @TableField(value = "type")
    @ApiModelProperty(value="收支类型：开仓；2-平仓；")
    private Byte type;

    /**
     * 持仓量
     */
    @TableField(value = "amount")
    @ApiModelProperty(value="持仓量")
    private BigDecimal amount;

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