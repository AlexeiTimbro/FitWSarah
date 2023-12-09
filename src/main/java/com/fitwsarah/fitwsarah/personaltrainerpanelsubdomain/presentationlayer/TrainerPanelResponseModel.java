package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.presentationlayer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerPanelResponseModel {
    private String availabilityId;
    private String available;
    private String adminId;
    private String date;
}
