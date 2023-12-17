package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminPanelRequestModel {
    private String username;
    private String password;
    private String email;
    private String role;
    private String city;
}
