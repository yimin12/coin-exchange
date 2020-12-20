package com.yimin.disruptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 17:12
 *   @Description :
 *
 */
@Data
@ConfigurationProperties(prefix = "spring.disruptor")
public class DisruptorProperties {

    /**
     * 缓冲区的大小
     */
    private Integer ringBufferSize = 1924 * 1024;

    /**
     * 是否支持多生产者
     */
    private boolean isMultiProducer = false;
}
