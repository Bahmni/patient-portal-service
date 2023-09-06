package org.bahmni.module.patientportal.controllers;

import org.bahmni.module.patientportal.dto.OtpLoginResponse;
import org.bahmni.module.patientportal.dto.OtpVerificationRequest;
import org.bahmni.module.patientportal.dto.OtpVerifiedResponse;
import org.bahmni.module.patientportal.dto.UserVerifiedResponse;
import org.bahmni.module.patientportal.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@CrossOrigin
@RestController
@RequestMapping("/openmrs/api/v1/patient")
public class PatientController {

    @Autowired
    private PatientService service;

    @GetMapping(path = "/login/{patientIdentifier}")
    public ResponseEntity<OtpLoginResponse> patientLogin(@PathVariable String patientIdentifier) throws IOException, ParseException {
        OtpLoginResponse response = service.patientLogin(patientIdentifier);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/verify", 
    consumes = {MediaType.APPLICATION_JSON_VALUE}, 
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OtpVerifiedResponse> patientVerify(@RequestBody OtpVerificationRequest request) throws IOException, ParseException {
        UserVerifiedResponse verifiedResponse = service.patientVerification(request.getOtp(), request.getPatientIdentifier(), request.getSessionUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + verifiedResponse.getAuthToken());
        return new ResponseEntity<>(verifiedResponse.getOtpVerified(), headers, HttpStatus.OK);
    }
}
