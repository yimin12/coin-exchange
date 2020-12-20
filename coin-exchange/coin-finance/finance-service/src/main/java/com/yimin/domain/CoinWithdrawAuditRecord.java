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
    * 提币审核记录
    */
@ApiModel(value="com-yimin-domain-CoinWithdrawAuditRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "coin_withdraw_audit_record")
public class CoinWithdrawAuditRecord {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 提币订单号
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="提币订单号")
    private Long orderId;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private Byte status;

    /**
     * 审核备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="审核备注")
    private String remark;

    /**
     * 当前审核级数
     */
    @TableField(value = "step")
    @ApiModelProperty(value="当前审核级数")
    private Byte step;

    /**
     * 审核人ID
     */
    @TableField(value = "audit_user_id")
    @ApiModelProperty(value="审核人ID")
    private Long auditUserId;

    /**
     * 审核人
     */
    @TableField(value = "audit_user_name")
    @ApiModelProperty(value="审核人")
    private String auditUserName;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;
}