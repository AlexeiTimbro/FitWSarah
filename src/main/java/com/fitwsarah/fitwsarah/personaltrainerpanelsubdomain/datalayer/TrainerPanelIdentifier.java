package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class TrainerPanelIdentifier {
    private String availabilityId;

    TrainerPanelIdentifier(){
        this.availabilityId = UUID.randomUUID().toString();
    }

    public String getAvailabilityId() {
        return availabilityId;
    }
}
