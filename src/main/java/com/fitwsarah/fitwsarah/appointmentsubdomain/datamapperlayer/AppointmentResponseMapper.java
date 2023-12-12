package com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanel;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer.AdminPanelResponseModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper {

    @Mapping(expression = "java(appointment.getAppointmentIdentifier().getAppointmentId())", target = "appointmentId")
    @Mapping(expression = "java(appointment.getAdminPanelIdentifier().getAdminId())", target = "adminId")
    @Mapping(expression = "java(appointment.getAccountIdentifier().getAccountId())", target = "accountId")
    @Mapping(expression = "java(appointment.getFitnessPackageIdentifier().getServiceId())", target = "serviceId")
    AppointmentResponseModel entityToResponseModel(Appointment appointment);

    List<AppointmentResponseModel> entityListToResponseModelList(List<Appointment> appointments);
}
