package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccountResponseModel {
    private String account_Id;
    private String username;
    private String email;
    private String city;
}
