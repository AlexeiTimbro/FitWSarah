package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FitnessPackageResponseModel {
    public String serviceId;
    public String promoId;
    public String title;
    public String duration;
    public String description;
    public Double price;
}
