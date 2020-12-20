package com.yimin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TradeAreaDto implements java.io.Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 交易区名称
     */
    private String name;
    /**
     * 交易区代码
     */
    private String code;
    /**
     * 类型：1-数字货币交易；2-创新交易使用；
     */
    private Integer type;
    /**
     * 结算币种（仅创新交易需要使用）
     */
    private Long coinId;
    /**
     * 结算币种名称（仅创新交易需要使用）
     */
    private String coinName;
    /**
     * 排序
     */
    private int sort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date lastUpdateTime;

    /**
     * 交易区内所有市场Id(逗号分隔)
     */
    private String marketIds;
}
