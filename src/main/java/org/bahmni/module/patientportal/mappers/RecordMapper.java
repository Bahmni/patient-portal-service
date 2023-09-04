package org.bahmni.module.patientportal.mappers;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bahmni.module.patientportal.openmrs.records.RecordFullRepresentation;
import org.bahmni.webclients.ObjectMapperRepository;
import org.hl7.fhir.r4.model.Bundle;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RecordMapper {

    private final ObjectMapper objectMapper;

    public RecordMapper() {
        this.objectMapper = ObjectMapperRepository.objectMapper;
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public RecordFullRepresentation mapFullRecord(String recordJSON, String record) throws IOException, ParseException {
        JsonNode rootNode = objectMapper.readTree(recordJSON);
        JsonNode resultsNode = rootNode.get(record);
        FhirContext fhirContext = FhirContext.forR4();
        IParser parser = fhirContext.newJsonParser();
        parser.setPrettyPrint(true);

        RecordFullRepresentation recordsList = new RecordFullRepresentation();
        List<JsonNode> records = new ArrayList<>();

        for (JsonNode resultNode : resultsNode) {
            JsonNode bundle = resultNode.get("bundle");
            Bundle bundleResource = parser.parseResource(Bundle.class, bundle.toString());
            String resource = parser.encodeResourceToString(bundleResource);
            records.add(objectMapper.readTree(resource));
        }
        recordsList.setRecordList(records);
        return recordsList;
    }

}

