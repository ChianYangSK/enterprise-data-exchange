package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReplayAttackChecker {
    private final StringRedisTemplate redis;
    private final ExchangeProperties properties;
    private final Map<String, Long> fallback = new ConcurrentHashMap<>();

    @Autowired
    public ReplayAttackChecker(@Autowired(required = false) StringRedisTemplate redis, ExchangeProperties properties) {
        this.redis = redis;
        this.properties = properties;
    }

    public boolean isFirstRequest(String nonce) {
        if (nonce == null || nonce.trim().isEmpty()) return false;
        long ttl = properties.getSecurity().getTimestampExpireSeconds();
        if (redis != null) {
            Boolean accepted = redis.opsForValue().setIfAbsent("security:nonce:" + nonce, "1", Duration.ofSeconds(ttl));
            return Boolean.TRUE.equals(accepted);
        }
        long now = System.currentTimeMillis();
        fallback.entrySet().removeIf(e -> e.getValue() < now);
        return fallback.putIfAbsent(nonce, now + ttl * 1000) == null;
    }
}
