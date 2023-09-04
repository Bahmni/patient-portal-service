package org.bahmni.module.patientportal.controllers;

import org.bahmni.module.patientportal.openmrs.visits.Visit;
import org.bahmni.module.patientportal.openmrs.visits.VisitHistoryFullRepresentation;
import org.bahmni.module.patientportal.services.VisitService;
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
@RequestMapping("/openmrs/api/v1/visit")
public class VisitController {

    @Autowired
    private VisitService service;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

   @GetMapping(path = "/all")
    public ResponseEntity<VisitHistoryFullRepresentation> fetchAllVisits(@RequestHeader("Authorization") String authHeader) throws IOException, ParseException {
       if(!jwtTokenUtils.validateJwtToken(authHeader)) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
       }
       VisitHistoryFullRepresentation visitHistory = service.fetchAllVisits(jwtTokenUtils.getPatientByToken(authHeader));
       return new ResponseEntity<>(visitHistory, HttpStatus.OK);
    }

    @GetMapping(path = "/each/{visitUuid}")
    public ResponseEntity<Visit> fetchSingleVisit(@RequestHeader("Authorization") String authHeader, @PathVariable String visitUuid) throws IOException, ParseException {
       if(!jwtTokenUtils.validateJwtToken(authHeader)) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
       }
       Visit visit = service.fetchVisit(visitUuid);
       return new ResponseEntity<>(visit, HttpStatus.OK);
    }

}
