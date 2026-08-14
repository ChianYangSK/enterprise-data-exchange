package com.enterprise.exchange.controller;

import com.enterprise.exchange.dto.request.LoginRequest;
import com.enterprise.exchange.dto.response.*;
import com.enterprise.exchange.service.AuthExchangeService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthExchangeController {
    private final AuthExchangeService service;

    public AuthExchangeController(AuthExchangeService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest http) {
        return ApiResponse.success(service.login(request, http.getRemoteAddr()));
    }
}
