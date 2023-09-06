package org.bahmni.module.patientportal.services;

import org.bahmni.module.patientportal.dto.OtpLoginResponse;
import org.bahmni.module.patientportal.dto.OtpVerifiedResponse;
import org.bahmni.module.patientportal.dto.UserVerifiedResponse;
import org.bahmni.module.patientportal.openmrs.patient.OpenMRSPatientFullRepresentation;
import org.bahmni.module.patientportal.openmrs.patient.OpenMRSPersonAttribute;
import org.bahmni.module.patientportal.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.bahmni.module.patientportal.utils.JwtTokenUtils;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private OpenMRSService openMRSService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    UUID uuid = UUID.randomUUID();

    public PatientService() {
    }

    public OpenMRSPatientFullRepresentation fetchPatientDetails(String patientIdentifier) throws IOException, ParseException {
        OpenMRSPatientFullRepresentation patient = openMRSService.getPatient(patientIdentifier);
        if ( patient == null ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.PATIENT_NOT_FOUND);
        }
        return patient;
    }

    public String fetchPatientNumber(OpenMRSPatientFullRepresentation patient) {
        List<OpenMRSPersonAttribute> personAttributes = patient.getPerson().getAttributes();
        System.out.println(personAttributes);

        if (personAttributes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.MOBILE_NOT_FOUND);
        }

        for (OpenMRSPersonAttribute attribute : personAttributes) {
            if (attribute.getAttributeType().getDisplay().equals(Constants.PHONE_ATTRIBUTE)) {
                return attribute.getValue().toString();
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.MOBILE_NOT_FOUND);
    }

    public OtpLoginResponse patientLogin(String patientIdentifier ) throws IOException, ParseException {
        OpenMRSPatientFullRepresentation person = fetchPatientDetails(patientIdentifier);
        String personNumber = fetchPatientNumber(person);
        if (otpService.generateOtp(personNumber, uuid.toString())) {
            return new OtpLoginResponse(patientIdentifier, uuid.toString(), Constants.OTP_SENT_MESSAGE);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.OTP_GENERATION_ERROR);
        }
    }

    public UserVerifiedResponse patientVerification(String otp, String patientIdentifier, String sessionUuid) throws IOException, ParseException {
        OpenMRSPatientFullRepresentation person = fetchPatientDetails(patientIdentifier);
        String patientUuid = person.getUuid();
        String personName = person.getPerson().getPreferredName().getGivenName();
        if (otpService.verifyOtp(otp, sessionUuid)) {
            OtpVerifiedResponse response = new OtpVerifiedResponse(personName, Constants.OTP_VERIFIED_MESSAGE);
            String authToken = jwtTokenUtils.generateJwtToken(patientUuid);
            return new UserVerifiedResponse(response, authToken);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.OTP_VERIFICATION_ERROR);
        }
    }
}
