package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackRequestModel {
    private String accountId;
    private Integer stars;
    private String content;
}
