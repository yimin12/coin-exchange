package com.yimin.task;


import com.yimin.constant.Constants;
import com.yimin.dto.MarketDto;
import com.yimin.event.KlineEvent;
import com.yimin.feign.MarketServiceFeign;
import com.yimin.rocket.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class TopicKLineTask {

    @Autowired
    private MarketServiceFeign marketServiceFeign ;


    // 注入Redis+ Source
    @Autowired
    private StringRedisTemplate redisTemplate ;

    @Autowired
    private Source source ;

    private ExecutorService executor = null;

    {
        executor = new ThreadPoolExecutor(
                5,
                10,
                100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(30),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 每30秒推送K线数据
     * 推送时:它是由executor实际上做数据的推送
     */
    @Scheduled(fixedRate = 30000)
    public void pushKline() {
        List<MarketDto> marketDtos = marketServiceFeign.tradeMarkets();
        if(!CollectionUtils.isEmpty(marketDtos)){
            for (MarketDto marketDto : marketDtos) {
                // 自己的new 的对象
                KlineEvent klineEvent = new KlineEvent(marketDto.getSymbol().toLowerCase(),"market.%s.kline.%s", Constants.REDIS_KEY_TRADE_KLINE);
                // 注入一个值
                klineEvent.setRedisTemplate(redisTemplate);
                // 注入一个值
                klineEvent.setSource(source);
                executor.submit(klineEvent) ;
            }
        }

    }
}