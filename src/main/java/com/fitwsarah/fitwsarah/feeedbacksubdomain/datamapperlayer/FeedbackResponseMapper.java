package com.fitwsarah.fitwsarah.feeedbacksubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackResponseMapper {
    @Mapping(expression = "java(feedback.getFeedbackIdentifier().getFeedbackId())", target = "feedbackId")
    FeedbackResponseModel entityToResponseModel(Feedback feedback);

    List<FeedbackResponseModel> entityListToResponseModelList(List<Feedback> feedbacks);
}
