package com.yimin.rocket;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 使用Source 发送数据
 */
public interface Source {

    @Output("group_message_out")
    MessageChannel subscribeGroupOutput() ;
}
