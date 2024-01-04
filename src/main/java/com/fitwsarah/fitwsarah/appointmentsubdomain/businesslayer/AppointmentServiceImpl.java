package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    private AppointmentResponseMapper appointmentResponseMapper;

    private AppointmentRequestMapper appointmentRequestMapper;


    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentResponseMapper appointmentResponseMapper, AppointmentRequestMapper appointmentRequestMapper){
        this.appointmentRepository = appointmentRepository;
        this.appointmentResponseMapper = appointmentResponseMapper;
        this.appointmentRequestMapper = appointmentRequestMapper;
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointments() {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAll());
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointmentsByAccountId(String accountId) {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAppointmentByAccountIdentifier_AccountId(accountId));
    }

    @Override
    public AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId) {
        return appointmentResponseMapper.entityToResponseModel(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId));
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
    public AppointmentResponseModel updateAppointmentStatus(String appointmentId, String status) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);
        appointment.setStatus(Status.valueOf(Status.CANCELLED.toString()));
        appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }


    @Override
    public void removeAppointment(String appointmentId) {

    }
}
