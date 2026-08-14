package com.enterprise.exchange.service;

import com.enterprise.exchange.audit.*;
import com.enterprise.exchange.client.InnerSystemClient;
import com.enterprise.exchange.dto.response.PatientResponse;
import com.enterprise.exchange.mask.DataMaskService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PatientExchangeService {
    private final InnerSystemClient client;
    private final DataMaskService masking;
    private final AuditLogService audit;

    public PatientExchangeService(InnerSystemClient client, DataMaskService masking, AuditLogService audit) {
        this.client = client;
        this.masking = masking;
        this.audit = audit;
    }

    public PatientResponse getPatient(String id, String ip) {
        Map<String, String> p = client.getPatient(id);
        PatientResponse response = new PatientResponse(p.get("patientId"), masking.maskName(p.get("name")), masking.maskIdCard(p.get("idCard")), masking.maskPhone(p.get("phone")));
        audit.record(new AuditRecord(java.util.UUID.randomUUID().toString(), "external-client", "/api/v1/patients/" + id, "GET", true, ip, "Masked patient response returned"));
        return response;
    }
}
