package org.bahmni.module.patientportal.dto;

public class OtpVerifiedResponse {

    private final String name;

    private final String message;

    public OtpVerifiedResponse(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

}
