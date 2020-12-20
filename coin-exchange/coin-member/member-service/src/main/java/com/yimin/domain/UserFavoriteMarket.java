package com.yimin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    * 用户收藏交易市场
    */
@ApiModel(value="com-yimin-domain-UserFavoriteMarket")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_favorite_market")
public class UserFavoriteMarket {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 交易对类型：1-币币交易；2-创新交易；
     */
    @TableField(value = "type")
    @ApiModelProperty(value="交易对类型：1-币币交易；2-创新交易；")
    private Integer type;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 交易对ID
     */
    @TableField(value = "market_id")
    @ApiModelProperty(value="交易对ID")
    private Long marketId;
}