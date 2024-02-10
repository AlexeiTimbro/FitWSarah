package com.fitwsarah.fitwsarah.feedbacksubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackResponseMapper {
    @Mapping(expression = "java(feedback.getFeedbackIdentifier().getFeedbackId())", target = "feedbackId")
    FeedbackResponseModel entityToResponseModel(Feedback feedback);

    List<FeedbackResponseModel> entityListToResponseModelList(List<Feedback> feedbacks);
}
