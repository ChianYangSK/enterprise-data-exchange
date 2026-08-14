package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class TokenManager {
    private final ExchangeProperties properties;

    public TokenManager(ExchangeProperties properties) {
        this.properties = properties;
    }

    public String createToken(String subject) {
        long now = System.currentTimeMillis() / 1000;
        String h = b64("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String p = b64("{\"sub\":\"" + subject + "\",\"iat\":" + now + ",\"exp\":" + (now + properties.getSecurity().getJwtExpireSeconds()) + "}");
        return h + "." + p + "." + sign(h + "." + p);
    }

    public boolean validateToken(String token) {
        return getSubject(token) != null;
    }

    public String getSubject(String token) {
        try {
            String[] p = token.split("\\.");
            if (p.length != 3 || !MessageDigest.isEqual(sign(p[0] + "." + p[1]).getBytes(StandardCharsets.UTF_8), p[2].getBytes(StandardCharsets.UTF_8)))
                return null;
            String json = new String(Base64.getUrlDecoder().decode(p[1]), StandardCharsets.UTF_8);
            long exp = Long.parseLong(json.replaceAll(".*\\\"exp\\\":(\\d+).*", "$1"));
            return exp < System.currentTimeMillis() / 1000 ? null : json.replaceAll(".*\\\"sub\\\":\\\"([^\\\"]+).*", "$1");
        } catch (Exception e) {
            return null;
        }
    }

    private String b64(String v) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(v.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String value) {
        try {
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(new SecretKeySpec(properties.getSecurity().getJwtSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(m.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to sign JWT", e);
        }
    }
}
