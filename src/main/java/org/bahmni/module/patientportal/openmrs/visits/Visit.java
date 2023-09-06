package org.bahmni.module.patientportal.openmrs.visits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Visit {

    private String Uuid;

    private VisitType visitType;

    @JsonProperty("startDatetime")
    private String startDate;

    @JsonProperty("stopDatetime")
    private String stopDate;

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String Uuid) {
        this.Uuid = Uuid;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return  stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

}
