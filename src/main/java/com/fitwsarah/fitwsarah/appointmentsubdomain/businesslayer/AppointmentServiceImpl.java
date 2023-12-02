package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public List<AppointmentResponseModel> getAllAppointments() {
        return null;
    }

    @Override
    public AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId) {
        return null;
    }

    @Override
    public AppointmentResponseModel addAppointment(AppointmentRequestModel appointmentRequestModel, String appointmentId) {
        return null;
    }

    @Override
    public AppointmentResponseModel updateAppointment(AppointmentRequestModel appointmentRequestModel, String appointmentId) {
        return null;
    }

    @Override
    public void removeAppointment(String appointmentId) {

    }
}
