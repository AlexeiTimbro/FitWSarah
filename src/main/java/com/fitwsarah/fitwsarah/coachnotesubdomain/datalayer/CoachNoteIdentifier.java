package com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class CoachNoteIdentifier {

    private String coachNoteId;

    public CoachNoteIdentifier(){
        this.coachNoteId = UUID.randomUUID().toString();
    }

    public String getCoachNoteId() {
        return coachNoteId;
    }

    public void setCoachNoteId(String coachNoteId) {
        this.coachNoteId = coachNoteId;
    }
}
