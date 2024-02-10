package com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class FeedbackIdentifier {
    private String feedbackId;
    FeedbackIdentifier(){
        this.feedbackId = UUID.randomUUID().toString();
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId){
        this.feedbackId = feedbackId;
    }
}
