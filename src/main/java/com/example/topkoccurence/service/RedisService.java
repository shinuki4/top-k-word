package com.example.topkoccurence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *
     * @param key to store in redis
     * @param value object to store in redis
     */
    public void saveToRedis(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    /**
     *
     * @param key to search redis
     * @return the value attach to the key
     */
    public Object getFromRedis(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }
}
