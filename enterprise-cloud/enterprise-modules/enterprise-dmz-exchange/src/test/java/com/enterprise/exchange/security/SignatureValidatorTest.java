package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignatureValidatorTest {
    @Test
    void acceptsCorrectSignatureAndRejectsIncorrectOne() {
        ExchangeProperties p = new ExchangeProperties();
        p.getSecurity().setSignatureSecret("test-secret");
        SignatureValidator v = new SignatureValidator(p);
        long now = System.currentTimeMillis() / 1000;
        String signature = v.sign(now, "nonce-1", "{}");
        assertTrue(v.validate(now, "nonce-1", "{}", signature));
        assertFalse(v.validate(now, "nonce-1", "{}", "bad"));
    }
}
