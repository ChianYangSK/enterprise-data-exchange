package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenManagerTest {
    @Test
    void createsAndValidatesJwtWithSubject() {
        TokenManager manager = new TokenManager(new ExchangeProperties());
        String token = manager.createToken("demo");
        assertTrue(manager.validateToken(token));
        assertEquals("demo", manager.getSubject(token));
        assertFalse(manager.validateToken(token + "x"));
    }
}
