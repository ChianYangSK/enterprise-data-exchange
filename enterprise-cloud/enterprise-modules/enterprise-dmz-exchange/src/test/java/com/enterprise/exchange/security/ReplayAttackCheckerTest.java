package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReplayAttackCheckerTest {
    @Test
    void acceptsNonceOnlyOnce() {
        ReplayAttackChecker checker = new ReplayAttackChecker(null, new ExchangeProperties());
        assertTrue(checker.isFirstRequest("nonce-1"));
        assertFalse(checker.isFirstRequest("nonce-1"));
    }
}
