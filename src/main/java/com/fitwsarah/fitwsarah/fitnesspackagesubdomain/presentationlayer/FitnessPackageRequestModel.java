package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FitnessPackageRequestModel {
    private String title;
    private String duration;
    private String description;
    private Double price;
}
