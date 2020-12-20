package com.yimin.rocket;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Source {


    /**
     * 盘口数据的输出
     * @return
     */
    @Output("trade_plate_out")
    MessageChannel plateOut() ;


    /**
     * 完成订单数据的输出
     * @return
     */
    @Output("completed_orders_out")
    MessageChannel completedOrdersOut() ;


    /**
     * 交易记录的输入
     * @return
     */
    @Output("exchange_trades_out")
    MessageChannel exchangeTradesOut() ;


    /**
     * 取消单的输出
     * @return
     */
    @Output("cancel_order_out")
    MessageChannel cancelOrderOut() ;
}
