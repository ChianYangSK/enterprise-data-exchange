package com.enterprise.exchange.service;

import com.enterprise.exchange.audit.*;
import com.enterprise.exchange.dto.request.LoginRequest;
import com.enterprise.exchange.dto.response.LoginResponse;
import com.enterprise.exchange.security.TokenManager;
import org.springframework.stereotype.Service;

@Service
public class AuthExchangeService {
    private final TokenManager tokens;
    private final AuditLogService audit;

    public AuthExchangeService(TokenManager tokens, AuditLogService audit) {
        this.tokens = tokens;
        this.audit = audit;
    }

    public LoginResponse login(LoginRequest request, String ip) {
        String token = tokens.createToken(request.getUsername());
        audit.record(new AuditRecord(java.util.UUID.randomUUID().toString(), request.getUsername(), "/api/v1/auth/login", "POST", true, ip, "Demo token issued"));
        return new LoginResponse(token);
    }
}
