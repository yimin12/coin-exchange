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
    * 提现表
    */
@ApiModel(value="com-yimin-domain-CashWithdrawals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "cash_withdrawals")
public class CashWithdrawals {
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
     * 币种ID
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="币种ID")
    private Long coinId;

    /**
     * 资金账户ID
     */
    @TableField(value = "account_id")
    @ApiModelProperty(value="资金账户ID")
    private Long accountId;

    /**
     * 数量（提现金额）
     */
    @TableField(value = "num")
    @ApiModelProperty(value="数量（提现金额）")
    private BigDecimal num;

    /**
     * 手续费
     */
    @TableField(value = "fee")
    @ApiModelProperty(value="手续费")
    private BigDecimal fee;

    /**
     * 到账金额
     */
    @TableField(value = "mum")
    @ApiModelProperty(value="到账金额")
    private BigDecimal mum;

    /**
     * 开户人
     */
    @TableField(value = "truename")
    @ApiModelProperty(value="开户人")
    private String truename;

    /**
     * 银行名称
     */
    @TableField(value = "bank")
    @ApiModelProperty(value="银行名称")
    private String bank;

    /**
     * 银行所在省
     */
    @TableField(value = "bank_prov")
    @ApiModelProperty(value="银行所在省")
    private String bankProv;

    /**
     * 银行所在市
     */
    @TableField(value = "bank_city")
    @ApiModelProperty(value="银行所在市")
    private String bankCity;

    /**
     * 开户行
     */
    @TableField(value = "bank_addr")
    @ApiModelProperty(value="开户行")
    private String bankAddr;

    /**
     * 银行账号
     */
    @TableField(value = "bank_card")
    @ApiModelProperty(value="银行账号")
    private String bankCard;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 当前审核级数
     */
    @TableField(value = "step")
    @ApiModelProperty(value="当前审核级数")
    private Byte step;

    /**
     * 状态：0-待审核；1-审核通过；2-拒绝；3-提现成功；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：0-待审核；1-审核通过；2-拒绝；3-提现成功；")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="更新时间")
    private Date lastUpdateTime;

    /**
     * 最后确认提现到账时间
     */
    @TableField(value = "last_time")
    @ApiModelProperty(value="最后确认提现到账时间")
    private Date lastTime;


    @TableField(exist = false)
    @ApiModelProperty(value = "用户的名称")
    private String username  ;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户的真实名称")
    private String realName ;
}