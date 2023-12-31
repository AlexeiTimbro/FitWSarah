package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments();
    List<AppointmentResponseModel> getAllAppointmentsByAccountId(String accountId);

    AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId);

    AppointmentResponseModel addAppointment(AppointmentRequestModel appointmentRequestModel, String appointmentId);

    AppointmentResponseModel updateAppointment(AppointmentRequestModel appointmentRequestModel, String appointmentId);

    AppointmentResponseModel updateAppointmentStatus(String appointmentId, String status);

    void removeAppointment(String appointmentId);
}
