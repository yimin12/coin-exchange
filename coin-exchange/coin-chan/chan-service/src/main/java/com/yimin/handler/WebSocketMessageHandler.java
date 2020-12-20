package com.yimin.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimin.vo.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/20 11:57
 *   @Description :
 *
 */
@Component
@Slf4j
public class WebSocketMessageHandler implements IWsMsgHandler {


    /**
     * 处理握手
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientIp = httpRequest.getClientIp();
        log.info("开始和{}客户端建立连接", clientIp);
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        log.info("和客户端握手成功");
    }


    /**
     * 当前websocket 前端发送一个Bytes 时,我们要做的处理
     *
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当前端发一个close方时,我们怎么处理
     *
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
//        Tio.remove(channelContext, "remove channelContext");
        return null;
    }

    /**
     * 当前端发送文本过来
     *
     * @param wsRequest
     * @param text
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {

//        log.info("前端发送数据过来了");
//        Tio.bindGroup(channelContext, "test");
//        return WsResponse.fromText("你好", "utf-8");
        if(Objects.equals("ping",text)){
            return "ok-pong";
        }
        log.info(text);
        JSONObject payload = JSON.parseObject(text);

        String sub = payload.getString("sub"); // 要订阅的组
        String req = payload.getString("req"); // 当前的request(预留字段)
        String cancel = payload.getString("cancel");// 要取消订阅的组
        String id = payload.getString("id"); // 订阅的id(预留字段)
        String authorization = payload.getString("authorization");

        if(StringUtils.hasText(sub)){
            // 订阅的组有内容
            Tio.bindGroup(channelContext, sub);
        }
        if(StringUtils.hasText(cancel)){
            Tio.unbindGroup(cancel, channelContext);
        }
        if(StringUtils.hasText(authorization) && authorization.startsWith("bearer ")){
            String token = authorization.replaceAll("bearer ","");
            // 2. 查询我们的菜单数据
            Jwt jwt = JwtHelper.decode(token);
            String jwtJsonStr = jwt.getClaims();
            JSONObject jwtJSon = JSON.parseObject(jwtJsonStr);
            String userId = jwtJSon.getString("user_name");
            // 有用户时绑定用户
            Tio.unbindUser(channelContext.getTioConfig(), userId);
        }
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCanceled(cancel) ;
        responseEntity.setSubbed(sub) ;
        responseEntity.setId(id) ;
        responseEntity.setStatus("OK") ;
        responseEntity.setCh(sub) ;
        responseEntity.setEvent(req) ;
        return responseEntity.build();
    }
}
