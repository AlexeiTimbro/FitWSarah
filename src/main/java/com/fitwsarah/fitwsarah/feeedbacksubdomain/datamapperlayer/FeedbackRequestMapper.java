package com.fitwsarah.fitwsarah.feeedbacksubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentIdentifier;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(feedbackIdentifier) ", target = "feedbackIdentifier", ignore = true)
    Feedback requestModelToEntity(FeedbackRequestModel feedbackRequestModel);
}
