package com.fitwsarah.fitwsarah.feedbacksubdomain.businesslayer;

import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackRequestModel;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackResponseModel;

import java.util.List;

public interface FeedbackService {

    List<FeedbackResponseModel> getAllFeedback(String feedbackId, String userid, String status);

    FeedbackResponseModel getFeedbackByFeedbackId(String feedbackId);

    FeedbackResponseModel addFeedback(FeedbackRequestModel feedbackRequestModel);

    FeedbackResponseModel updateFeedbackState(String status, String feedbackId);

    void removeFeedback(String feedbackId);
}
