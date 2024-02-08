package com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CoachNoteResponseModel {

    String coachNoteId;
    String accountId;
    String userId;
    String username;
    String content_EN;
    String content_FR;

}
