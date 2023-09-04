package org.bahmni.module.patientportal.mappers;

import org.bahmni.module.patientportal.openmrs.patient.OpenMRSPatientFullRepresentation;
import org.bahmni.webclients.ObjectMapperRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

public class OpenMRSPatientMapper {
    private final ObjectMapper objectMapper;

    public OpenMRSPatientMapper() {
        this.objectMapper = ObjectMapperRepository.objectMapper;
    }

    public OpenMRSPatientFullRepresentation mapFullRepresentation(String patientJSON) throws IOException {
        JsonNode rootNode = objectMapper.readTree(patientJSON);
        JsonNode resultsNode = rootNode.get("results");
        if (resultsNode.isArray() && !resultsNode.isEmpty()) {
            JsonNode firstResultNode = resultsNode.get(0);
            return objectMapper.treeToValue(firstResultNode, OpenMRSPatientFullRepresentation.class);
        } else {
            return null;
        }
    }

    public OpenMRSPatientFullRepresentation mapFull(String patientJSON) throws IOException {
        OpenMRSPatientFullRepresentation patient = objectMapper.readValue(patientJSON, OpenMRSPatientFullRepresentation.class);
        if (patient == null || isEmpty(patient)) {
            return null;
        } else {
            return patient;
        }
    }
}