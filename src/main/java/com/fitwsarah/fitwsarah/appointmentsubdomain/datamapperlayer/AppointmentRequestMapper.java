package com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(appointmentIdentifier) ", target = "appointmentIdentifier", ignore = true)
    @Mapping( expression = "java(adminPanelIdentifier) ", target = "adminPanelIdentifier", ignore = true)
    Appointment requestModelToEntity(AppointmentRequestModel appointmentRequestModel);
}
