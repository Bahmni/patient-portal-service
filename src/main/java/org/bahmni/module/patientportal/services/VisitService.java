package org.bahmni.module.patientportal.services;

import org.bahmni.module.patientportal.openmrs.visits.Visit;
import org.bahmni.module.patientportal.openmrs.visits.VisitHistoryFullRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class VisitService {

    @Autowired
    private OpenMRSService openMRSService;

    public VisitHistoryFullRepresentation fetchAllVisits(String patientUuid) throws IOException, ParseException {
        return openMRSService.getVisitDetails(patientUuid);
    }

    public Visit fetchVisit(String visitUuid) throws IOException, ParseException {
        return openMRSService.getIndividualVisit(visitUuid);
    }

}
