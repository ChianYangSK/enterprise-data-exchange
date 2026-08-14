package com.enterprise.exchange.client;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Boundary for restricted calls to an internal healthcare API. This portfolio implementation returns synthetic data only.
 */
@Component
public class InnerSystemClient {
    public Map<String, String> getPatient(String patientId) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("patientId", patientId);
        data.put("name", "Zhang San");
        data.put("idCard", "110101199001011234");
        data.put("phone", "13800138000");
        return data;
    }

    public Map<String, String> getSchedule(String patientId) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("patientId", patientId);
        data.put("scheduleDate", "2026-08-13");
        data.put("department", "Dialysis Center");
        data.put("status", "SCHEDULED");
        return data;
    }
}
