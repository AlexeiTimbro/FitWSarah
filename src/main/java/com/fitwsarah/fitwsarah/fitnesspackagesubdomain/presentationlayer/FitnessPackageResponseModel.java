package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FitnessPackageResponseModel {
    public String serviceId;
    public String promoId;
    public String title;
    public String duration;
    public String description;
    public Double price;
}
