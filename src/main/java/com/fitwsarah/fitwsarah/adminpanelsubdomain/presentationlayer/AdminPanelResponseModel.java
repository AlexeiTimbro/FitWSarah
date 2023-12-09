package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPanelResponseModel {
    private String adminId;
    private String username;
    private String password;
    private String email;
    private String role;
    private String city;
}
