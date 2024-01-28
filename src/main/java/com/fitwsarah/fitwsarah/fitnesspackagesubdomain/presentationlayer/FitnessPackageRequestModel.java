package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.Status;
import lombok.*;

@Getter
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FitnessPackageRequestModel {
    private Status status;
    private String title_EN;
    private String title_FR;
    private String duration;
    private String description_EN;
    private String description_FR;
    private String otherInformation_EN;
    private String otherInformation_FR;
    private Double price;
}
