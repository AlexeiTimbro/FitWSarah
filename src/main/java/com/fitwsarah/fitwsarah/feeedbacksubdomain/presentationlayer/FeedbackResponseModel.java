package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseModel {
    private String feedbackId;
    private String accountId;
    private Integer stars;
    private String content;
}
