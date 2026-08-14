package com.enterprise.exchange.dto.response;

public class ScheduleResponse {
    private final String patientId, scheduleDate, department, status;

    public ScheduleResponse(String patientId, String scheduleDate, String department, String status) {
        this.patientId = patientId;
        this.scheduleDate = scheduleDate;
        this.department = department;
        this.status = status;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        return status;
    }
}
