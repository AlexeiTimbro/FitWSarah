package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseModel {
    private String accountId;
    private String username;
    private String password;
    private String email;
    private String city;
}
