package com.yimin.disruptor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.LifecycleAware;
import lombok.extern.slf4j.Slf4j;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 17:17
 *   @Description :
 *
 */
@Slf4j
public class DisruptorHandlerException implements ExceptionHandler {


    /**
     * <p>Strategy for handling uncaught exceptions when processing an event.</p>
     *
     * <p>If the strategy wishes to terminate further processing by the {@link BatchEventProcessor}
     * then it should throw a {@link RuntimeException}.</p>
     *
     * @param ex       the exception that propagated from the {@link EventHandler}.
     * @param sequence of the event which cause the exception.
     * @param event    being processed when the exception occurred.  This can be null.
     */
    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        log.info("handleEventException Exception===>{} , sequence==> {} ,event===>{}",ex.getMessage(),sequence,event);
    }

    /**
     * Callback to notify of an exception during {@link LifecycleAware#onStart()}
     *
     * @param ex throw during the starting process.
     */
    @Override
    public void handleOnStartException(Throwable ex) {
        log.info("OnStartHandler Exception===>{} ",ex.getMessage());
    }

    /**
     * Callback to notify of an exception during {@link LifecycleAware#onShutdown()}
     *
     * @param ex throw during the shutdown process.
     */
    @Override
    public void handleOnShutdownException(Throwable ex) {
        log.info("OnShutdownHandler Exception===>{} ",ex.getMessage());
    }
}
