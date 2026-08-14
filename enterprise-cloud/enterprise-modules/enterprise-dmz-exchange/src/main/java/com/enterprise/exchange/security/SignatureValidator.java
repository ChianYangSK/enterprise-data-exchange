package com.enterprise.exchange.security;

import com.enterprise.exchange.config.ExchangeProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class SignatureValidator {
    private final ExchangeProperties properties;

    public SignatureValidator(ExchangeProperties properties) {
        this.properties = properties;
    }

    public boolean validate(long timestamp, String nonce, String requestBody, String signature) {
        if (nonce == null || signature == null || isExpired(timestamp)) return false;
        return MessageDigest.isEqual(sign(timestamp, nonce, requestBody).getBytes(StandardCharsets.UTF_8), signature.getBytes(StandardCharsets.UTF_8));
    }

    public String sign(long timestamp, String nonce, String requestBody) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(properties.getSecurity().getSignatureSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getEncoder().encodeToString(mac.doFinal((timestamp + "\n" + nonce + "\n" + (requestBody == null ? "" : requestBody)).getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to calculate request signature", e);
        }
    }

    private boolean isExpired(long timestamp) {
        return Math.abs((System.currentTimeMillis() / 1000) - timestamp) > properties.getSecurity().getTimestampExpireSeconds();
    }
}
