package com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer;

import com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer.AuthenticationRequestModel;
import com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer.AuthenticationResponseModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;

import java.util.List;

public interface FeedbackService {

    List<FeedbackResponseModel> getAllFeedback();

    FeedbackResponseModel getFeedbackByFeedbackId(String feedbackId);

    FeedbackResponseModel addFeedback(AuthenticationRequestModel authenticationRequestModel, String feedbackId);

    FeedbackResponseModel updateFeedback(AuthenticationRequestModel authenticationRequestModel, String feedbackId);

    void removeFeedback(String feedbackId);
}
