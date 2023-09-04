package org.bahmni.module.patientportal.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    @Value("${OtpBaseUrl}")
    private String OtpBaseUrl;

    public boolean generateOtp(String mobileNumber, String sessionId) {
        return true;
    }

    public boolean verifyOtp(String otp, String sessionId) {
        return true;
    }
}
