package com.enterprise.exchange.dto.response;

public class PatientResponse {
    private final String patientId, name, idCard, phone;

    public PatientResponse(String patientId, String name, String idCard, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getPhone() {
        return phone;
    }
}
