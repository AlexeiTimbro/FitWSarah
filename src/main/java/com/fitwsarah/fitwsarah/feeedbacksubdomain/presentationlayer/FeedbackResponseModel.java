package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FeedbackResponseModel {
    private String feedbackId;
    private String userId;
    private Integer stars;
    private String content;
    private State status;
}
