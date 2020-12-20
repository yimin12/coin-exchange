package com.yimin.match;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 20:46
 *   @Description :
 *
 */
@Data
@ConfigurationProperties(prefix = "spring.match")
public class MatchEngineProperties {

    /**
     * 交易对的信息
     */
    private Map<String, CoinScale> symbols;

    @Data
    public static  class  CoinScale {
        /**
         * 交易币种的精度
         */
        private int coinScale;

        /**
         * 基币的精度
         */
        private int baseCoinScale;
    }
}
