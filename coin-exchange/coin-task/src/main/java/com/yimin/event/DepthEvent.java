package com.yimin.event;

import com.alibaba.fastjson.JSONObject;
import com.yimin.dto.MarketDto;
import com.yimin.enums.DepthMergeType;
import com.yimin.feign.MarketServiceFeign;
import com.yimin.model.MessagePayload;
import com.yimin.rocket.Source;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 深度盘口数据事件
 */
@Component
@Slf4j
public class DepthEvent implements Event {

    @Autowired
    private Source source;

    @Autowired
    private MarketServiceFeign marketServiceFeign;

    /**
     * 盘口深度数据的群(组/频道)
     */
    private static final String DEPTH_GROUP = "market.%s.depth.step%s"; // 1:%s 具体的那个交易对   %s 深度的类型

    /**
     * 推送市场合并深度
     */
    @Override
    public void handle() {

        // 1 查询市场
        List<MarketDto> marketDtoList = marketServiceFeign.tradeMarkets();

        if (CollectionUtils.isEmpty(marketDtoList)) {
            return;
        }
        for (MarketDto marketDto : marketDtoList) {
            @NotNull String symbol = marketDto.getSymbol(); // 交易对的名称
            // 查询该交易对所有的深度数据/盘口数据
            for (DepthMergeType mergeType : DepthMergeType.values()) {
                // 2 通过交易对以及合并的类型查询所有的深度数据
                String data = marketServiceFeign.depthData(symbol, mergeType.getValue());
                MessagePayload messagePayload = new MessagePayload();
                messagePayload.setChannel(String.format(DEPTH_GROUP, symbol.toLowerCase(), mergeType.getValue()));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tick", data);
                messagePayload.setBody(jsonObject.toJSONString());
                source.subscribeGroupOutput()
                        .send(MessageBuilder.withPayload(messagePayload)
                                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build()
                        );
            }
        }
    }
}
