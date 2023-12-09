package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoOfferResponseModel {
    private String promoId;
    private String title;
    private Boolean availability;
    private String description;
    private Double price;
}
