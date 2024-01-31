package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    private final AccountRepository accountRepository;
    private JavaMailSender javaMailSender;

    public AppointmentServiceImpl(JavaMailSender javaMailSender, AppointmentRepository appointmentRepository, AppointmentResponseMapper appointmentResponseMapper, AppointmentRequestMapper appointmentRequestMapper, AccountRepository accountRepository){
        this.appointmentRepository = appointmentRepository;
        this.appointmentResponseMapper = appointmentResponseMapper;
        this.appointmentRequestMapper = appointmentRequestMapper;
        this.accountRepository = accountRepository;
        this.javaMailSender = javaMailSender;
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
    public List<AppointmentResponseModel> getAllAppointmentsByUserId(String userId) {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAllAppointmentsByUserId(userId));
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointmentsByUserIdAndStatus(String userId, String status) {
        return appointmentResponseMapper.entityListToResponseModelList(appointmentRepository.findAllAppointmentByUserIdAndStatus(userId, Status.valueOf(status)));
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
    public AppointmentResponseModel updateAppointmentDetails(AppointmentRequestModel appointmentRequestModel, String appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);

        // Check if the appointment exists
        if (appointment == null) {
            throw new EntityNotFoundException("Appointment with ID " + appointmentId + " not found");
        }

        // Update appointment details
        appointment.setStatus(appointmentRequestModel.getStatus());
        appointment.setLocation(appointmentRequestModel.getLocation());
        appointment.setFirstName(appointmentRequestModel.getFirstName());
        appointment.setLastName(appointmentRequestModel.getLastName());
        appointment.setPhoneNum(appointmentRequestModel.getPhoneNum());
        appointment.setDate(appointmentRequestModel.getDate());
        appointment.setTime(appointmentRequestModel.getTime());

        // Save the updated appointment
        appointmentRepository.save(appointment);

        // Return the updated appointment details
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }


    @Override
    public AppointmentResponseModel updateAppointmentStatus(String appointmentId, String status) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);
        appointment.setStatus(Status.valueOf(Status.CANCELLED.toString()));
        appointmentRepository.save(appointment);
        sendCancellationEmail(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }

    @Override
    public AppointmentResponseModel handleAppointmentRequest(String appointmentId, String status) {
        Appointment appointment = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId);
        appointment.setStatus(Status.valueOf(Status.SCHEDULED.toString()));
        appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }


    @Override
    public void removeAppointment(String appointmentId) {

    }

    private void sendCancellationEmail(Appointment appointment) {
        try {
            String userId = appointment.getUserId();
            Account account = accountRepository.findAccountByUserId(userId);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("fitwithsarahfitness@gmail.com");
            helper.setTo(account.getEmail());
            helper.setSubject("Appointment Cancellation");
            helper.setText("Your appointment has been canceled.");

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
