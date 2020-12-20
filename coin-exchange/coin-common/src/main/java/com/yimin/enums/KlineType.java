package com.yimin.enums;

/**
 * K线类型
 */
public enum KlineType {
    
    ONE_MINUTES("1min"),
    FIVE_MINUTES("5min"),
    FIFTEEN_MINUTES("15min"),
    THIRTY_MINUTES("30min"),
    ONE_HOUR("1hour"),
    TWO_HOURS("2hour"),
    FOUR_HOURS("4hour"),
    SIX_HOURS("6hour"),
    TWELVE_HOURS("12hour"),
    ONE_DAY("1day"),
    ONE_WEEK("1week"),
    ONE_MONTH("1mon"),
    ONE_YEAR("1year");

    private String value;

    KlineType(final String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static KlineType getByValue(String value) {
        for (KlineType klineType : KlineType.values()) {
            if (klineType.getValue().equals(value)) {
                return klineType;
            }
        }
        return null;
    }
}
