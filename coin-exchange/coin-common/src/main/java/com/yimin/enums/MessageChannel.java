package com.yimin.enums;

/**
 * 订阅的频道名称
 */
public enum MessageChannel {


    ORDER_TX("order.tx"),
    ORDER_CANCEL("order.cancel"),
    ORDER_IN("order.in"),
    MATCH_IN("match.in"),
    ORDER_DELAY_NOTIFY("order.delay.notify"),
    SYNC_ACCOUNT("sync.account"),
    MARKET_REFRESH("market.refresh"),
    BONUS("bonus"),
    REGISTER_REWARD("register.reward"),
    FINANCE_RECHARGE_SUCCESS("finance.recharge.success"),
    FINANCE_WITHDRAW_RESULT("finance.withdraw.result"),
    RECHARGE_ADDRESS("plant.user.address");

    private String channel;

    MessageChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}