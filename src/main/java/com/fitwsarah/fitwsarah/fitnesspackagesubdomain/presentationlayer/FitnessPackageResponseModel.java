package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.Status;
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
    public Status status;
    public String title_EN;
    public String title_FR;
    public String duration;
    public String description_EN;
    public String description_FR;
    public String otherInformation_EN;
    public String otherInformation_FR;
    public Double price;
}
