package com.enterprise.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchange")
public class ExchangeProperties {
    private final Security security = new Security();
    private final Internal internal = new Internal();
    private final RateLimit rateLimit = new RateLimit();

    public Security getSecurity() {
        return security;
    }

    public Internal getInternal() {
        return internal;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public static class Security {
        private String signatureSecret = "change-me";
        private long timestampExpireSeconds = 300;
        private String jwtSecret = "change-me";
        private long jwtExpireSeconds = 3600;

        public String getSignatureSecret() {
            return signatureSecret;
        }

        public void setSignatureSecret(String signatureSecret) {
            this.signatureSecret = signatureSecret;
        }

        public long getTimestampExpireSeconds() {
            return timestampExpireSeconds;
        }

        public void setTimestampExpireSeconds(long timestampExpireSeconds) {
            this.timestampExpireSeconds = timestampExpireSeconds;
        }

        public String getJwtSecret() {
            return jwtSecret;
        }

        public void setJwtSecret(String jwtSecret) {
            this.jwtSecret = jwtSecret;
        }

        public long getJwtExpireSeconds() {
            return jwtExpireSeconds;
        }

        public void setJwtExpireSeconds(long jwtExpireSeconds) {
            this.jwtExpireSeconds = jwtExpireSeconds;
        }
    }

    public static class Internal {
        private String baseUrl = "http://localhost:8081";

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    public static class RateLimit {
        private boolean enabled = true;
        private long maxRequests = 60;
        private long windowSeconds = 60;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public long getMaxRequests() {
            return maxRequests;
        }

        public void setMaxRequests(long value) {
            maxRequests = value;
        }

        public long getWindowSeconds() {
            return windowSeconds;
        }

        public void setWindowSeconds(long value) {
            windowSeconds = value;
        }
    }
}
