package org.bahmni.module.patientportal.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpVerificationRequest {
    private final String otp;
    private final String patientIdentifier;
    private final String sessionUuid;

    @JsonCreator
    public OtpVerificationRequest(
            @JsonProperty("otp") String otp,
            @JsonProperty("patientIdentifier") String patientIdentifier,
            @JsonProperty("sessionUuid") String sessionUuid) {
        this.otp = otp;
        this.patientIdentifier = patientIdentifier;
        this.sessionUuid = sessionUuid;
    }

    public String getOtp() {
        return otp;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public String getSessionUuid() {
        return sessionUuid;
    }
}
