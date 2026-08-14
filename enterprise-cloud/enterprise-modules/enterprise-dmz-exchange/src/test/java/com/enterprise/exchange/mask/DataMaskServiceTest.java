package com.enterprise.exchange.mask;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataMaskServiceTest {
    @Test
    void masksSensitiveValuesWithoutFailingOnEmptyInput() {
        DataMaskService s = new DataMaskService();
        assertEquals("110101********1234", s.maskIdCard("110101199001011234"));
        assertEquals("138****8000", s.maskPhone("13800138000"));
        assertEquals("", s.maskIdCard(""));
        assertNull(s.maskPhone(null));
    }
}
