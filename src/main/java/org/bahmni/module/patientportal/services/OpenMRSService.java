package org.bahmni.module.patientportal.services;

import org.bahmni.module.patientportal.mappers.RecordMapper;
import org.bahmni.module.patientportal.mappers.VisitMapper;
import org.bahmni.module.patientportal.openmrs.auth.ConnectionDetails;
import org.bahmni.module.patientportal.openmrs.auth.WebClientFactory;
import org.bahmni.module.patientportal.openmrs.patient.OpenMRSPatientFullRepresentation;
import org.bahmni.module.patientportal.mappers.OpenMRSPatientMapper;
import org.bahmni.module.patientportal.openmrs.records.RecordFullRepresentation;
import org.bahmni.module.patientportal.openmrs.visits.Visit;
import org.bahmni.module.patientportal.openmrs.visits.VisitHistoryFullRepresentation;
import org.bahmni.module.patientportal.utils.Constants;
import org.bahmni.webclients.HttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;

@Component
public class OpenMRSService {

    String patientRestUrl = "/openmrs/ws/rest/v1/patient?identifier=";

    String patientUuidRestUrl = "/openmrs/ws/rest/v1/patient/";

    String visitURL = "/openmrs/ws/rest/v1/visit?includeInactive=true&patient=";

    String individualVisitURL = "/openmrs/ws/rest/v1/visit/";

    String OPConsultURL = "/openmrs/ws/rest/v1/hip/opConsults/visit?";

    String DiagnosticReportURL = "/openmrs/ws/rest/v1/hip/diagnosticReports/visit?";

    String DischargeSummaryURL = "/openmrs/ws/rest/v1/hip/dischargeSummary/visit?";

    String ImmunizationURL = "/openmrs/ws/rest/v1/hip/immunizationRecord/visit?";

    String PrescriptionURL = "/openmrs/ws/rest/v1/hip/prescriptions/visit?";

    public OpenMRSPatientFullRepresentation getPatient(String patientUuid) throws IOException, ParseException {
        HttpClient webClient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String patientJSON = webClient.get(URI.create(urlPrefix + patientRestUrl + patientUuid + "&v=full"));
        return new OpenMRSPatientMapper().mapFullRepresentation(patientJSON);
    }

    public OpenMRSPatientFullRepresentation getPatientByUuid(String patientUuid) throws IOException, ParseException {
        HttpClient webClient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String patientJSON = webClient.get(URI.create(urlPrefix + patientUuidRestUrl + patientUuid + "?v=full"));
        return new OpenMRSPatientMapper().mapFull(patientJSON);
    }

    public VisitHistoryFullRepresentation getVisitDetails(String patientUuid) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String visitDetails = webclient.get(URI.create(urlPrefix + visitURL + patientUuid + "&v=custom:(uuid,visitType,startDatetime,stopDatetime,location,encounters:(uuid))"));
        return new VisitMapper().mapFullVisitList(visitDetails);
    }

    public Visit getIndividualVisit(String visitUuid) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String visitDetails = webclient.get(URI.create(urlPrefix + individualVisitURL + visitUuid + "?v=custom:(uuid,visitType,startDatetime,stopDatetime,encounters:(uuid,encounterDatetime,provider:(display),encounterType:(display)))"));
        return new VisitMapper().mapVisit(visitDetails);
    }

    public RecordFullRepresentation getOPConsult(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String OPConsult = webclient.get(URI.create(urlPrefix + OPConsultURL + "patientId=" + patientUuid + "&visitUuid=" + visitUuid + "&fromDate=" + fromDate  + "&toDate=" + endDate));
        System.out.println(OPConsult);
        return new RecordMapper().mapFullRecord(OPConsult, "opConsults");
    }

    public RecordFullRepresentation getDiagnosticReport(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String DiagnosticReport = webclient.get(URI.create(urlPrefix + DiagnosticReportURL + "patientId=" + patientUuid + "&visitUuid=" + visitUuid + "&fromDate=" + fromDate  + "&toDate=" + endDate));
        System.out.println(DiagnosticReport);
        return new RecordMapper().mapFullRecord(DiagnosticReport, "diagnosticReports");
    }

    public RecordFullRepresentation getDischargeSummary(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String DischargeSummary = webclient.get(URI.create(urlPrefix + DischargeSummaryURL + "patientId=" + patientUuid + "&visitUuid=" + visitUuid + "&fromDate=" + fromDate  + "&toDate=" + endDate));
        System.out.println(DischargeSummary);
        return new RecordMapper().mapFullRecord(DischargeSummary, "dischargeSummary");
    }

    public RecordFullRepresentation getImmunization(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String Immunization = webclient.get(URI.create(urlPrefix + ImmunizationURL + "patientId=" + patientUuid + "&visitUuid=" + visitUuid + "&fromDate=" + fromDate  + "&toDate=" + endDate));
        System.out.println(Immunization);
        return new RecordMapper().mapFullRecord(Immunization, "immunizationRecord");
    }

    public RecordFullRepresentation getPrescription(String visitUuid, String patientUuid, String fromDate, String endDate) throws IOException, ParseException {
        HttpClient webclient = WebClientFactory.getClient();
        String urlPrefix = getURLPrefix();
        String Prescription = webclient.get(URI.create(urlPrefix + PrescriptionURL + "patientId=" + patientUuid + "&visitUuid=" + visitUuid + "&fromDate=" + fromDate  + "&toDate=" + endDate));
        System.out.println(Prescription);
        return new RecordMapper().mapFullRecord(Prescription, "prescriptions");
    }

    private String getURLPrefix() {
        org.bahmni.webclients.ConnectionDetails connectionDetails = ConnectionDetails.get();
        String authenticationURI = connectionDetails.getAuthUrl();

        URL openMRSAuthURL;
        try {
            openMRSAuthURL = new URL(authenticationURI);
        } catch (MalformedURLException e) {
            throw new RuntimeException(Constants.INVALID_URI + authenticationURI);
        }
        return String.format("%s://%s", openMRSAuthURL.getProtocol(), openMRSAuthURL.getAuthority());
    }

}
