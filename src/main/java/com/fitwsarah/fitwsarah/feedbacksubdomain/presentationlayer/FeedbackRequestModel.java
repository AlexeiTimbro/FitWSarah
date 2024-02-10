package com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FeedbackRequestModel {
    private String userId;
    private Integer stars;
    private String content;
    private State status;
}
