package org.bahmni.module.patientportal.services;

import org.bahmni.module.patientportal.openmrs.records.RecordFullRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class RecordService {

    @Autowired
    private OpenMRSService openMRSService;

    public RecordFullRepresentation fetchOpConsult(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        return openMRSService.getOPConsult(visitUuid, patientUuid, fromDate, endDate);
    }

    public RecordFullRepresentation fetchDiagnosticReport(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        return openMRSService.getDiagnosticReport(visitUuid, patientUuid, fromDate, endDate);
    }

    public RecordFullRepresentation fetchDischargeSummary(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        return openMRSService.getDischargeSummary(visitUuid, patientUuid, fromDate, endDate);
    }

    public RecordFullRepresentation fetchImmunization(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        return openMRSService.getImmunization(visitUuid, patientUuid, fromDate, endDate);
    }

    public RecordFullRepresentation fetchPrescription(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        return openMRSService.getPrescription(visitUuid, patientUuid, fromDate, endDate);
    }
}
