package org.bahmni.module.patientportal.dto;

public class UserVerifiedResponse {

    private final OtpVerifiedResponse otpVerified;

    private final String authToken;

    public UserVerifiedResponse(OtpVerifiedResponse otpVerified, String authToken) {
        this.otpVerified= otpVerified;
        this.authToken = authToken;
    }

    public OtpVerifiedResponse getOtpVerified() {
        return otpVerified;
    }

    public String getAuthToken() {
        return authToken;
    }
}
