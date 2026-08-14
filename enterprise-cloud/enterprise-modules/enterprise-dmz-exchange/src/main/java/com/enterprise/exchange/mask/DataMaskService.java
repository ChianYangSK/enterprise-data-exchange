package com.enterprise.exchange.mask;

import org.springframework.stereotype.Service;

@Service
public class DataMaskService {
    public String maskName(String value) {
        if (value == null || value.isEmpty()) return value;
        return value.charAt(0) + "*";
    }

    public String maskIdCard(String value) {
        if (value == null || value.isEmpty()) return value;
        if (value.length() <= 10) return stars(value.length());
        return value.substring(0, 6) + stars(value.length() - 10) + value.substring(value.length() - 4);
    }

    public String maskPhone(String value) {
        if (value == null || value.isEmpty()) return value;
        if (value.length() <= 7) return stars(value.length());
        return value.substring(0, 3) + stars(value.length() - 7) + value.substring(value.length() - 4);
    }

    private String stars(int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.max(0, count); i++) {
            result.append('*');
        }
        return result.toString();
    }
}
