package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class PromoIdentifier {
    private String promoId;

    public PromoIdentifier(){this.promoId= UUID.randomUUID().toString();}

    public String getPromoId() {
        return promoId;
    }
}
