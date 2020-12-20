package com.yimin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.starter.EnableTioWebSocketServer;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

import java.util.Date;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/20 11:54
 *   @Description :
 *
 */
@SpringBootApplication
@EnableTioWebSocketServer // 开启tio的websocket
//@EnableScheduling
public class ChanServiceApplication {

    @Autowired
    private TioWebSocketServerBootstrap bootstrap;

    public static void main(String[] args) {
        SpringApplication.run(ChanServiceApplication.class, args);
    }

//    // 测试场景，每五秒向前端推送一个信息
//    @Scheduled(fixedRate = 5000)
//    public void pushData(){
//
//        long time = new Date().getTime();
//        // 使用Tio， 推送的数据非常简单
//        Tio.sendToGroup(bootstrap.getServerTioConfig(), "test", WsResponse.fromText("现在是:" + time,"utf-8"));
//    }
}
