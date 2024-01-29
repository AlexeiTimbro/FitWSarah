package com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackRequestModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;

import java.util.List;

public interface FeedbackService {

    List<FeedbackResponseModel> getAllFeedback();

    FeedbackResponseModel getFeedbackByFeedbackId(String feedbackId);

    FeedbackResponseModel addFeedback(FeedbackRequestModel feedbackRequestModel);

    FeedbackResponseModel updateFeedbackState(String status, String feedbackId);

    void removeFeedback(String feedbackId);
}
