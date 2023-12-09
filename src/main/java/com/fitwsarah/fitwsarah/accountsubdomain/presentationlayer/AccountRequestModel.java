package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestModel {
    private String username;
    private String password;
    private String email;
    private String city;
}
