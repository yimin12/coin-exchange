package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.*;
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
    * 充值表
    */
@ApiModel(value="com-yimin-domain-CashRecharge")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cash_recharge")
public class CashRecharge {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "自增id")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 币种id
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value = "币种id")
    private Long coinId;

    /**
     * 币种名：cny，人民币；
     */
    @TableField(value = "coin_name")
    @ApiModelProperty(value = "币种名：cny，人民币；")
    private String coinName;

    /**
     * 数量（充值金额）
     */
    @TableField(value = "num")
    @ApiModelProperty(value = "数量（充值金额）")
    private BigDecimal num;

    /**
     * 手续费
     */
    @TableField(value = "fee")
    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    /**
     * 手续费币种
     */
    @TableField(value = "feecoin")
    @ApiModelProperty(value = "手续费币种")
    private String feecoin;

    /**
     * 成交量（到账金额）
     */
    @TableField(value = "mum")
    @ApiModelProperty(value = "成交量（到账金额）")
    private BigDecimal mum;

    /**
     * 类型：alipay，支付宝；cai1pay，财易付；bank，银联；
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "类型：alipay，支付宝；cai1pay，财易付；bank，银联；")
    private String type;

    /**
     * 充值订单号
     */
    @TableField(value = "tradeno")
    @ApiModelProperty(value = "充值订单号")
    private String tradeno;

    /**
     * 第三方订单号
     */
    @TableField(value = "outtradeno")
    @ApiModelProperty(value = "第三方订单号")
    private String outtradeno;

    /**
     * 充值备注备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "充值备注备注")
    private String remark;

    /**
     * 审核备注
     */
    @TableField(value = "audit_remark")
    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    /**
     * 当前审核级数
     */
    @TableField(value = "step")
    @ApiModelProperty(value = "当前审核级数")
    private Byte step;

    /**
     * 状态：0-待审核；1-审核通过；2-拒绝；3-充值成功；
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "状态：0-待审核；1-审核通过；2-拒绝；3-充值成功；")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date created;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time",fill=FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdateTime;

    /**
     * 银行卡账户名
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "银行卡账户名")
    private String name;

    /**
     * 开户行
     */
    @TableField(value = "bank_name")
    @ApiModelProperty(value = "开户行")
    private String bankName;

    /**
     * 银行卡号
     */
    @TableField(value = "bank_card")
    @ApiModelProperty(value = "银行卡号")
    private String bankCard;

    /**
     * 最后确认到账时间。
     */
    @TableField(value = "last_time")
    @ApiModelProperty(value = "最后确认到账时间。")
    private Date lastTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户的名称")
    private String username  ;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户的真实名称")
    private String realName ;
}