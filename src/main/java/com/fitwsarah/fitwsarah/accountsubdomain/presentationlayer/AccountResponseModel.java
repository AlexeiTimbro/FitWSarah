package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AccountResponseModel {
    private String accountId;
    private String userId;
    private String username;
    private String email;
    private String city;
    private Date dateCreated;
}
