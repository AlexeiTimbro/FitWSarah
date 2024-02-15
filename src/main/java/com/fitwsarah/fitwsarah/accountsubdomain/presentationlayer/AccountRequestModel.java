package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
public class AccountRequestModel {
    String userId;
    String username;
    String email;
    String city;
}
