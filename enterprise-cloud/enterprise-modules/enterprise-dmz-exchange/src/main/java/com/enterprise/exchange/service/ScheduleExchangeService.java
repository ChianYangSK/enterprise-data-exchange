package com.enterprise.exchange.service;

import com.enterprise.exchange.audit.*;
import com.enterprise.exchange.client.InnerSystemClient;
import com.enterprise.exchange.dto.response.ScheduleResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScheduleExchangeService {
    private final InnerSystemClient client;
    private final AuditLogService audit;

    public ScheduleExchangeService(InnerSystemClient client, AuditLogService audit) {
        this.client = client;
        this.audit = audit;
    }

    public ScheduleResponse getSchedule(String id, String ip) {
        Map<String, String> s = client.getSchedule(id);
        audit.record(new AuditRecord(java.util.UUID.randomUUID().toString(), "external-client", "/api/v1/schedules/" + id, "GET", true, ip, "Schedule response returned"));
        return new ScheduleResponse(s.get("patientId"), s.get("scheduleDate"), s.get("department"), s.get("status"));
    }
}
