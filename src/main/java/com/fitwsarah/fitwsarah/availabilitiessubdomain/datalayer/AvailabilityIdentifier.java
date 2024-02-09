package com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer;


import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AvailabilityIdentifier {
    private String availabilityId;

    public AvailabilityIdentifier(){
        this.availabilityId = UUID.randomUUID().toString();
    }

    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }
}
