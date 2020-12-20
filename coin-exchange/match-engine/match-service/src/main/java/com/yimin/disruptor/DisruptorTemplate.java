package com.yimin.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.yimin.model.Order;

/**
 * 在boot里面使用它发送消息
 */
public class DisruptorTemplate {

    private static final EventTranslatorOneArg<OrderEvent, Order> TRANSLATOR = new EventTranslatorOneArg<OrderEvent, Order>() {

        public void translateTo(OrderEvent event, long sequence, Order input) {
            event.setSource(input);
        }
    };
    private final RingBuffer<OrderEvent> ringBuffer;

    public DisruptorTemplate(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 我们使用DisruptorTemplate 时,就使用它的onData方法
     * @param input
     */
    public void onData(Order input) {
        ringBuffer.publishEvent(TRANSLATOR, input);
    }
}

