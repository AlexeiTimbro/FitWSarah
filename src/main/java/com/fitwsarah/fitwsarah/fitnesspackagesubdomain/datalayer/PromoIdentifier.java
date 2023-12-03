package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import java.util.UUID;

public class PromoIdentifier {
    private String promoId;

    PromoIdentifier(){this.promoId= UUID.randomUUID().toString();}

    public String getPromoId() {
        return promoId;
    }
}
