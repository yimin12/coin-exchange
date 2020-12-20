package com.yimin.rocket;

import com.alibaba.fastjson.JSON;
import com.yimin.model.MessagePayload;
import com.yimin.vo.ResponseEntity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tio.core.Tio;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

import javax.validation.constraints.NotNull;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/20 12:14
 *   @Description :
 *
 */
@Component
@Slf4j
public class RocketMessageListener {

    @Autowired
    private TioWebSocketServerBootstrap tioWebSocketServerBootstrap;

    @StreamListener("tio_group")
    public void handlerMessage(MessagePayload message){
        log.info("接收到rocketmq的消息========>{}", JSON.toJSONString(message));
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCh(message.getChannel());
        responseEntity.put("result", message.getBody());

        // 推送给前端用户就可以
        if(StringUtils.hasText(message.getUserId())){
            Tio.sendToUser(tioWebSocketServerBootstrap.getServerTioConfig(), message.getUserId(), responseEntity.build());
            return;
        }
        @NotNull String group = message.getChannel();
        Tio.sendToGroup(tioWebSocketServerBootstrap.getServerTioConfig(), group, responseEntity.build());
    }
}
