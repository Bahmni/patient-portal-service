package org.bahmni.module.patientportal.controllers;

import org.bahmni.module.patientportal.openmrs.records.RecordFullRepresentation;
import org.bahmni.module.patientportal.services.RecordService;
import org.bahmni.module.patientportal.utils.Constants;
import org.bahmni.module.patientportal.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;

@CrossOrigin
@RestController
@RequestMapping("/openmrs/api/v1/record")
public class RecordController {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private RecordService service;

    @GetMapping(path = "/opConsult/{visitUuid}")
    public ResponseEntity<RecordFullRepresentation> fetchOpConsult(@RequestHeader("Authorization") String authHeader, @PathVariable String visitUuid, @RequestParam String fromDate, @RequestParam String endDate) throws IOException, ParseException {
        if(!jwtTokenUtils.validateJwtToken(authHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
        }
        RecordFullRepresentation opConsult = service.fetchOpConsult(visitUuid, jwtTokenUtils.getPatientByToken(authHeader), fromDate, endDate);
        return new ResponseEntity<>(opConsult, HttpStatus.OK);
    }

    @GetMapping(path = "/diagnosticReport/{visitUuid}")
    public ResponseEntity<RecordFullRepresentation> fetchDiagnosticReport(@RequestHeader("Authorization") String authHeader, @PathVariable String visitUuid, @RequestParam String fromDate, @RequestParam String endDate) throws IOException, ParseException {
        if(!jwtTokenUtils.validateJwtToken(authHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
        }
        RecordFullRepresentation diagnosticReport = service.fetchDiagnosticReport(visitUuid, jwtTokenUtils.getPatientByToken(authHeader), fromDate, endDate);
        return new ResponseEntity<>(diagnosticReport, HttpStatus.OK);
    }

    @GetMapping(path = "/dischargeSummary/{visitUuid}")
    public ResponseEntity<RecordFullRepresentation> fetchDischargeSummary(@RequestHeader("Authorization") String authHeader, @PathVariable String visitUuid, @RequestParam String fromDate, @RequestParam String endDate) throws IOException, ParseException {
        if(!jwtTokenUtils.validateJwtToken(authHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
        }
        RecordFullRepresentation dischargeSummary = service.fetchDischargeSummary(visitUuid, jwtTokenUtils.getPatientByToken(authHeader), fromDate, endDate);
        return new ResponseEntity<>(dischargeSummary, HttpStatus.OK);
    }

    @GetMapping(path = "/immunization/{visitUuid}")
    public ResponseEntity<RecordFullRepresentation> fetchImmunization(@RequestHeader("Authorization") String authHeader, @PathVariable String visitUuid, @RequestParam String fromDate, @RequestParam String endDate) throws IOException, ParseException {
        if(!jwtTokenUtils.validateJwtToken(authHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
        }
        RecordFullRepresentation immunization = service.fetchImmunization(visitUuid, jwtTokenUtils.getPatientByToken(authHeader), fromDate, endDate);
        return new ResponseEntity<>(immunization, HttpStatus.OK);
    }

    @GetMapping(path = "/prescription/{visitUuid}")
    public ResponseEntity<RecordFullRepresentation> fetchPrescription(@RequestHeader("Authorization") String authHeader, @PathVariable String visitUuid, @RequestParam String fromDate, @RequestParam String endDate) throws IOException, ParseException {
        if(!jwtTokenUtils.validateJwtToken(authHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
        }
        RecordFullRepresentation prescription = service.fetchPrescription(visitUuid, jwtTokenUtils.getPatientByToken(authHeader), fromDate, endDate);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }
}
