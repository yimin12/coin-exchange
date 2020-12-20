package com.yimin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "交易对的资产")
public class SymbolAssetVo {

    /**
     * 报价货币余额/GCN
     */
    @ApiModelProperty(value = "报价货币余额")
    private BigDecimal buyAmount;

    /**
     * 报价货币单位
     */
    @ApiModelProperty(value = "报价货币单位")
    private String buyUnit;

    /**
     * 报价货币冻结额度
     */
    @ApiModelProperty(value = "报价货币冻结额度")
    private BigDecimal buyLockAmount;

    /**
     * 买方手续费费率
     */
    @ApiModelProperty(value = "买方手续费费率")
    private BigDecimal buyFeeRate;

    /**
     * 基础货币余额 /BTC
     */
    @ApiModelProperty(value = "基础货币余额")
    private BigDecimal sellAmount;

    /**
     * 基础货币单位
     */
    @ApiModelProperty(value = "基础货币单位")
    private String sellUnit;

    /**
     * 基础货币冻结额度
     */
    @ApiModelProperty(value = "基础货币冻结额度")
    private BigDecimal sellLockAmount;

    /**
     * 卖方手续费费率
     */
    @ApiModelProperty(value = "卖方手续费费率")
    private BigDecimal sellFeeRate;
}
