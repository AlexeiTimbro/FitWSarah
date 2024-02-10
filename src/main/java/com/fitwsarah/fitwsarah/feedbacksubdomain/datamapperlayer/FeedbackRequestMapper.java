package com.fitwsarah.fitwsarah.feedbacksubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(feedbackIdentifier) ", target = "feedbackIdentifier", ignore = true)
    Feedback requestModelToEntity(FeedbackRequestModel feedbackRequestModel);
}
