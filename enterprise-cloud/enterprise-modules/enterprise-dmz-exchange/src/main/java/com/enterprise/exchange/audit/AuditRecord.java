package com.enterprise.exchange.audit;

import java.time.Instant;

public class AuditRecord {
    private final String id, timestamp, requestId, clientId, api, method, ip, message;
    private final boolean success;

    public AuditRecord(String requestId, String clientId, String api, String method, boolean success, String ip, String message) {
        this.id = java.util.UUID.randomUUID().toString();
        this.timestamp = Instant.now().toString();
        this.requestId = requestId;
        this.clientId = clientId;
        this.api = api;
        this.method = method;
        this.success = success;
        this.ip = ip;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getApi() {
        return api;
    }

    public String getMethod() {
        return method;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getIp() {
        return ip;
    }

    public String getMessage() {
        return message;
    }
}
