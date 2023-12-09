package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPanelRequestModel {
    private String username;
    private String password;
    private String email;
    private String role;
    private String city;
}
