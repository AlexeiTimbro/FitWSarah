package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResponseModel {
    private String accountId;
    private String username;
    private String password;
    private String email;
    private String city;

}
