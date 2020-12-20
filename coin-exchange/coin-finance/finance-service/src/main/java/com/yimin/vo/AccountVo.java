package com.yimin.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Data
@ApiModel(value = "账户资产的VO对象")
public class AccountVo {

    /**
     * 账户ID
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 币种id
     */
    private Long coinId;
    /**
     * 账号状态：1，正常；2，冻结；
     */
    private Integer accountStatus;
    /**
     * 币种可用金额
     */
    private BigDecimal balanceAmount;
    /**
     * 账面余额
     */
    private BigDecimal carryingAmount;
    /**
     * 币种冻结金额
     */
    private BigDecimal freezeAmount;
    /**
     * 累计充值金额
     */
    private BigDecimal rechargeAmount;
    /**
     * 累计提现金额
     */
    private BigDecimal withdrawalsAmount;
    /**
     * 净值
     */
    private BigDecimal netValue;
    /**
     * 占用保证金
     */
    private BigDecimal lockMargin;
    /**
     * 持仓盈亏/浮动盈亏
     */
    private BigDecimal floatProfit;
    /**
     * 总盈亏
     */
    private BigDecimal totalProfit;
    /**
     * 充值地址
     */
    private String recAddr;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 持仓盈亏汇总
     */
    private BigDecimal opsitionGainAndLossSummary;
    /**
     * 币种类型
     */
    private String coinType;
    /**
     * 币种名称
     */
    private String coinName;

    /**
     * 币种img
     */
    private String coinImgUrl;

    /**
     * 剩余币的个数
     */
    private BigDecimal coinNum;

    /**
     * 总的币个数
     */
    private BigDecimal totalNum;

    /**
     * 币种冻结金额
     */
    private BigDecimal coinFreezeNum;

    /**
     * 提币手续费率
     */
    private BigDecimal feeRate;

    /**
     * 提币固定手续费
     */
    private BigDecimal minFeeNum;

    /**
     * 提现开关
     */
    private int withdrawFlag;

    /**
     * 充值开关
     */
    private int rechargeFlag;

    /**
     * 单个币种CNY
     */
    private BigDecimal totalCny;

    /**
     * 当前对标基础结算货币价格
     */

    private BigDecimal currentPrice;

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount.setScale(8, RoundingMode.HALF_UP);
    }

    public void setCarryingAmount(BigDecimal carryingAmount) {
        this.carryingAmount = carryingAmount.setScale(8, RoundingMode.HALF_UP);
    }

    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount.setScale(8, RoundingMode.HALF_UP);
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount.setScale(8, RoundingMode.HALF_UP);
    }

    public void setWithdrawalsAmount(BigDecimal withdrawalsAmount) {
        this.withdrawalsAmount = withdrawalsAmount.setScale(8, RoundingMode.HALF_UP);
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue.setScale(8, RoundingMode.HALF_UP);
    }

    public void setLockMargin(BigDecimal lockMargin) {
        this.lockMargin = lockMargin.setScale(8, RoundingMode.HALF_UP);
    }

    public void setFloatProfit(BigDecimal floatProfit) {
        this.floatProfit = floatProfit.setScale(8, RoundingMode.HALF_UP);
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit.setScale(8, RoundingMode.HALF_UP);
    }
}
