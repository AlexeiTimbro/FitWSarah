package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FitnessPackageResponseModel {
    private String serviceId;
    private String promoId;
    private String title;
    private String duration;
    private String description;
    private Double price;
}
