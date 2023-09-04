package org.bahmni.module.patientportal.openmrs.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordFullRepresentation {

    private List<JsonNode> recordList;

    public List<JsonNode> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<JsonNode> recordList) {
        this.recordList = recordList;
    }
}
