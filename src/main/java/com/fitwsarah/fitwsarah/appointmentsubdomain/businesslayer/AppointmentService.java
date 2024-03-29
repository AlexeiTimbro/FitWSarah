package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments(String appointmentId, String accountId, String status);
    List<AppointmentResponseModel> getAllAppointmentsByUserId(String userId);
    List<AppointmentResponseModel> getAllAppointmentsByUserIdAndStatus(String userId, String status);
    AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId);

    AppointmentResponseModel addAppointment(AppointmentRequestModel appointmentRequestModel);

    AppointmentResponseModel updateAppointmentDetails(AppointmentRequestModel appointmentRequestModel, String appointmentId);

    AppointmentResponseModel updateAppointmentStatus(String appointmentId, String status);

    AppointmentResponseModel handleAppointmentRequest(String appointmentId, String status);

    AppointmentResponseModel updateAppointmentDateTime(String appointmentId, AppointmentRequestModel appointmentRequestModel);

}
