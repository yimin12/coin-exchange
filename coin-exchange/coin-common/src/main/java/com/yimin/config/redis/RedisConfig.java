package com.yimin.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 20:40
 *   @Description :
 *
 */
@Configuration
public class RedisConfig {
    /**
     * RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // redis key's serialization
        StringRedisSerializer keyRedisSerializer = new StringRedisSerializer();
        // redis value's serialization
        GenericJackson2JsonRedisSerializer valueRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(keyRedisSerializer);
        redisTemplate.setValueSerializer(valueRedisSerializer);
        redisTemplate.setHashKeySerializer(keyRedisSerializer);
        redisTemplate.setHashValueSerializer(valueRedisSerializer);
        return redisTemplate;
    }
}
