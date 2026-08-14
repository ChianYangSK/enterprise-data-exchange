package com.enterprise.exchange.exception;

public enum ErrorCode {
    VALIDATION_ERROR("400", "Request validation failed"), SECURITY_ERROR("401", "Security validation failed"), INTERNAL_ERROR("500", "Unexpected server error");
    private final String code, message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
