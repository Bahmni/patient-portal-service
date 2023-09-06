package org.bahmni.module.patientportal.mappers;

import org.bahmni.module.patientportal.openmrs.visits.Visit;
import org.bahmni.module.patientportal.openmrs.visits.VisitHistoryFullRepresentation;
import org.bahmni.webclients.ObjectMapperRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class VisitMapper {

    private final ObjectMapper objectMapper;

    public VisitMapper() {
        this.objectMapper = ObjectMapperRepository.objectMapper;
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public VisitHistoryFullRepresentation mapFullVisitList(String visitJSON) throws IOException, ParseException {
        JsonNode rootNode = objectMapper.readTree(visitJSON);
        JsonNode resultsNode = rootNode.get("results");

        if (resultsNode.isArray() && !resultsNode.isEmpty()) {
            VisitHistoryFullRepresentation fullRepresentation = new VisitHistoryFullRepresentation();
            List<Visit> visitList = new ArrayList<>();

            for (JsonNode resultNode : resultsNode) {
                Visit visit = objectMapper.treeToValue(resultNode, Visit.class);
                visitList.add(visit);
            }

            fullRepresentation.setVisitList(visitList);
            return fullRepresentation;
        } else {
            return null;
        }
    }

    public Visit mapVisit(String visitJSON) throws IOException, ParseException {
        return objectMapper.readValue(visitJSON, Visit.class);
    }
}

