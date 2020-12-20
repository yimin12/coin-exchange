package com.yimin.service;

import com.yimin.constant.Constants;
import com.yimin.domain.Line;
import com.yimin.dto.CreateKLineDto;
import com.yimin.enums.KlineType;
import com.yimin.util.KlineTimeUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


@Component
public class TradeKlineService implements CommandLineRunner {

    // 当我们的交易完成(撮合)之后,就会触发k 线的生成
    public static BlockingQueue<CreateKLineDto> queue = new LinkedBlockingDeque<>();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            CreateKLineDto createKLineDto = queue.poll(); // 从里面获取一个数据,若没有数据,则会阻塞
            if (createKLineDto != null) {
                for (KlineType klineType : KlineType.values()) {
                    this.generateKLine(createKLineDto, klineType);
                }
            }
        }

    }

    /**
     * 为当前的交易数据生成K 线
     *
     * @param klineData
     * @param klineType
     */
    private void generateKLine(CreateKLineDto klineData, KlineType klineType) {
        // 1 获取之前该K 线的数据
        String redisKey = new StringBuilder(Constants.REDIS_KEY_TRADE_KLINE)
                .append(klineData.getSymbol().toLowerCase())
                .append(":")
                .append(klineType.getValue().toLowerCase()).toString();
        Long size = redisTemplate.opsForList().size(redisKey);
        DateTime dateTime = KlineTimeUtil.getKLineTime(klineType);
        // 2 之前没有该K 线的数据
        if (size == 0) {
            Line line = new Line(dateTime, klineData.getPrice(), klineData.getVolume());
            // 放在Redis 里面
            redisTemplate.opsForList().leftPush(redisKey, line.toKline());
            return;
        }

        // 3 之前有数据 ,获取最近的一个数据
        String historyKlineData = redisTemplate.opsForList().range(redisKey, 0, 1).get(0);
        Line historyKline = new Line(historyKlineData);
        // 4 若当前的时间: 是否还在上一个时间的区间内
        if (dateTime.compareTo(historyKline.getTime()) == 1) {

            // redis的容量是否超
            if (size > Constants.REDIS_MAX_CACHE_KLINE_SIZE) {
                redisTemplate.opsForList().rightPop(redisKey);
            }

            Line line = new Line();
            line.setTime(dateTime);
            // 如果我们当前的交易量为 0
            if (klineData.getVolume().compareTo(BigDecimal.ZERO) == 0) {
                line.setHigh(historyKline.getClose());
                line.setLow(historyKline.getClose());
                line.setOpen(historyKline.getClose());
                line.setClose(historyKline.getClose());
                line.setVolume(BigDecimal.ZERO) ; // 设置交易量为0
                // 放在Redis 里面
                redisTemplate.opsForList().leftPush(redisKey, line.toKline());
                return;
            }
            line.setOpen(klineData.getPrice());
            line.setClose(klineData.getPrice());

            // 最高价 ,最低价
            if (klineData.getPrice().compareTo(historyKline.getHigh()) == 1) {
                line.setHigh(klineData.getPrice());
                line.setLow(historyKline.getClose());
            }
            if (klineData.getPrice().compareTo(historyKline.getLow()) == 1) {
                line.setLow(klineData.getPrice());
                line.setHigh(historyKline.getClose());
            }

            historyKline.setClose(klineData.getPrice());
            redisTemplate.opsForList().set(redisKey, 0, historyKline.toKline());
            // 放最新的进入
            // 放在Redis 里面
            redisTemplate.opsForList().leftPush(redisKey, line.toKline());
            return;
        }

        if (klineData.getVolume().compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        historyKline.setClose(klineData.getPrice());

        if (klineData.getPrice().compareTo(historyKline.getHigh()) == 1) {
            historyKline.setHigh(klineData.getPrice());
        }

        if (klineData.getPrice().compareTo(historyKline.getLow()) == 1) {
            historyKline.setLow(klineData.getPrice());
        }
        historyKline.setVolume(historyKline.getVolume().add(klineData.getVolume()));
        redisTemplate.opsForList().set(redisKey, 0, historyKline.toKline());
    }


}
