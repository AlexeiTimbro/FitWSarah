package com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper {
    @Mapping(expression = "java(appointment.getAppointmentIdentifier().getAppointmentId())", target = "appointmentId")
    @Mapping(expression = "java(appointment.getAdminPanelIdentifier().getAdminId())", target = "adminId")
    @Mapping(expression = "java(appointment.getAccountIdentifier().getAccountId())", target = "accountId")
    @Mapping(expression = "java(appointment.getAvailability().getAvailableStatus())", target = "availableStatus")
    @Mapping(expression = "java(appointment.getAvailability().getAvailableDate())", target = "availableDate")
    AppointmentResponseModel entityToResponseModel(Appointment appointment);

    List<AppointmentResponseModel> entityListToResponseModelList(List<Appointment> appointments);
}
