package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class FitnessPackageIdentifier {
    private String serviceId;

    FitnessPackageIdentifier(){
        this.serviceId = UUID.randomUUID().toString();
    }

    public String getServiceId() {
        return serviceId;
    }
}
