package org.bahmni.module.patientportal.utils;

import org.bahmni.module.patientportal.openmrs.patient.OpenMRSPatientFullRepresentation;
import org.bahmni.module.patientportal.services.OpenMRSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;

@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expireIn}")
    private int expireIn;

    @Autowired
    OpenMRSService service;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateJwtToken(String patientUuid) {
        return Jwts.builder()
                .setSubject(patientUuid)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expireIn))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPatientByToken(String authHeader) {
        String authToken = authHeader.substring(7);
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken).getBody().getSubject();
    }

    public boolean validateJwtToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;  // Invalid token format
        }
        String jwtString = authHeader.substring(7);
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwtString).getBody();
            String patientUuid = claims.getSubject();
            OpenMRSPatientFullRepresentation patient = service.getPatientByUuid(patientUuid);
            if (patient == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.PATIENT_NOT_FOUND);
            }
            return true;
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.JWT_VALIDATION_ERROR);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
