package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestModel {
    private String accountId;
    private Integer stars;
    private String content;
}
