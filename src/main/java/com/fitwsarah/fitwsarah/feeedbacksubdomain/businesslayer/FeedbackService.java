package com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;

import java.util.List;

public interface FeedbackService {

    List<FeedbackResponseModel> getAllFeedback();

    FeedbackResponseModel getFeedbackByFeedbackId(String feedbackId);

    FeedbackResponseModel addFeedback(FeedbackResponseModel feedbackResponseModel, String feedbackId);

    FeedbackResponseModel updateFeedback(FeedbackResponseModel feedbackResponseModel, String feedbackId);

    void removeFeedback(String feedbackId);
}
