package com.enterprise.exchange.controller;

import com.enterprise.exchange.dto.response.*;
import com.enterprise.exchange.service.PatientExchangeService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientExchangeController {
    private final PatientExchangeService service;

    public PatientExchangeController(PatientExchangeService service) {
        this.service = service;
    }

    @GetMapping("/{patientId}")
    public ApiResponse<PatientResponse> getPatient(@PathVariable String patientId, HttpServletRequest request) {
        return ApiResponse.success(service.getPatient(patientId, request.getRemoteAddr()));
    }
}
