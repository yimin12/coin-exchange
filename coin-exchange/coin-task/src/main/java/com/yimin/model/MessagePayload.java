package com.yimin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePayload {

    /**
     * 用户的id
     */
    private String userId;


    /**
     * 订阅的组名称
     */
    @NonNull
    private String channel;

    /**
     * 消息的内容
     */
    @NonNull
    private String body;
}