package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<AppointmentResponseModel> getAllAppointments(String appointmentId, String accountId, String status){
        Set<Appointment> appointments = new HashSet<>();

        if(appointmentId != null){
            appointments.addAll(appointmentRepository.findAllAppointmentsByAppointmentIdentifier_AppointmentIdStartingWith(appointmentId));
        } else if(accountId != null){
            appointments.addAll(appointmentRepository.findAllAppointmentsByUserIdStartingWith(accountId));
        } else if(status != null){
            appointments.addAll(appointmentRepository.findAllAppointmentsByStatus(Status.valueOf(status)));
        } else {
            appointments.addAll(appointmentRepository.findAll());
        }

        return appointmentResponseMapper.entityListToResponseModelList(appointments.stream().sorted(Comparator.comparing(appointment -> appointment.getAppointmentIdentifier().getAppointmentId())).toList());
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointmentsByAccountId(String accountId) {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAllAppointmentsByUserId(accountId));
    }

    @Override
    public AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId) {
        return appointmentResponseMapper.entityToResponseModel(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId));
    }

    @Override
    public AppointmentResponseModel addAppointment(AppointmentRequestModel appointmentRequestModel) {
        Appointment appointment = appointmentRequestMapper.requestModelToEntity(appointmentRequestModel);
        appointment.setStatus(Status.REQUESTED);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(savedAppointment);
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
