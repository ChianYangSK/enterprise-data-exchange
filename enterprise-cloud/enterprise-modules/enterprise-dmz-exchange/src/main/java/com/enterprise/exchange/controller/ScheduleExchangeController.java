package com.enterprise.exchange.controller;

import com.enterprise.exchange.dto.response.*;
import com.enterprise.exchange.service.ScheduleExchangeService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleExchangeController {
    private final ScheduleExchangeService service;

    public ScheduleExchangeController(ScheduleExchangeService service) {
        this.service = service;
    }

    @GetMapping("/{patientId}")
    public ApiResponse<ScheduleResponse> getSchedule(@PathVariable String patientId, HttpServletRequest request) {
        return ApiResponse.success(service.getSchedule(patientId, request.getRemoteAddr()));
    }
}
