package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateLimitServiceTest {
    @Test
    void rejectsRequestsBeyondFixedWindowLimit() {
        ExchangeProperties p = new ExchangeProperties();
        p.getRateLimit().setMaxRequests(2);
        RateLimitService s = new RateLimitService(p);
        assertTrue(s.allow("client"));
        assertTrue(s.allow("client"));
        assertFalse(s.allow("client"));
    }
}
