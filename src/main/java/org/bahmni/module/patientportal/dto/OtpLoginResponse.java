package org.bahmni.module.patientportal.dto;

public class OtpLoginResponse {

    private final String patientIdentifier;
    private final String sessionUuid;

    private final String message;

    public OtpLoginResponse(String patientIdentifier, String sessionUuid, String message) {
        this.patientIdentifier = patientIdentifier;
        this.sessionUuid = sessionUuid;
        this.message = message;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public String getSessionUuid() {
        return sessionUuid;
    }

    public String getMessage() {
        return message;
    }

}
