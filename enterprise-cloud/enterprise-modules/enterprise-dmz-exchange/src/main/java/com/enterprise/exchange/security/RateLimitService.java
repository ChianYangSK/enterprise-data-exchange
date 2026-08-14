package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RateLimitService {
    private final ExchangeProperties p;
    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<String, Window>();

    public RateLimitService(ExchangeProperties p) {
        this.p = p;
    }

    public boolean allow(String key) {
        if (!p.getRateLimit().isEnabled()) return true;
        long now = System.currentTimeMillis() / 1000, bucket = now / p.getRateLimit().getWindowSeconds();
        Window w = windows.computeIfAbsent(key + ":" + bucket, k -> new Window());
        return w.count.incrementAndGet() <= p.getRateLimit().getMaxRequests();
    }

    private static class Window {
        private final AtomicLong count = new AtomicLong();
    }
}
