package com.choandyoo.jett.invitation.component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValues(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValues(String key, String value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    @Transactional
    public String getValues(String key) {
        Object values = redisTemplate.opsForValue().get(key);
        return (values != null) ? values.toString() : null;
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public void expireValues(String key, int timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public void setHashOps(String key, Map<String, String> data) {
        redisTemplate.opsForHash().putAll(key, data);
    }

    @Transactional
    public String getHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        Object value = hashOps.get(key, hashKey);
        return (value != null) ? value.toString() : null;
    }

    public void deleteHashOps(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public boolean checkExistsValue(String value) {
        return value != null && !value.isEmpty();
    }

    public static Duration toTomorrow() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tomorrow = now.plusDays(1);
        return Duration.between(now, tomorrow);
    }
}