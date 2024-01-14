package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class AppointmentServiceUnitTest {
    @InjectMocks
    AppointmentServiceImpl appointmentService;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAppointmentByAppointmentId_Should_Succeed() {


        String appointmentId = "uuid-appt1";


        Appointment appointment = new Appointment();
        appointment.getAppointmentIdentifier().setAppointmentId(appointmentId);


        AppointmentResponseModel responseModel = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-service1", Status.COMPLETED, "Location 1","","","","");


        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);


        // Use the appointmentResponseMapper mock to return the responseModel when mapping the appointment to response model

        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(responseModel);


        AppointmentResponseModel result = appointmentService.getAppointmentByAppointmentId(appointmentId);


        assertEquals(appointmentId, result.getAppointmentId());
    }

    @Test
    void getAllAppointmentsByAccountId_returnsNonEmptyList() {
        String accountId = "uuid-account1";
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        List<AppointmentResponseModel> responseModels = Arrays.asList(new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-service1", Status.COMPLETED, "Location 1","","","",""), new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-service1", Status.COMPLETED, "Location 1","","","",""));

        when(appointmentRepository.findAllAppointmentsByAccountId(accountId)).thenReturn(appointments);
        when(appointmentResponseMapper.entityListToResponseModelList(appointments)).thenReturn(responseModels);

        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByAccountId(accountId);

        assertEquals(responseModels, result);
    }

    @Test
    void getAllAppointmentsByAccountId_returnsEmptyList() {
        String accountId = "uuid-account1";
        List<Appointment> appointments = Collections.emptyList();

        when(appointmentRepository.findAllAppointmentsByAccountId(accountId)).thenReturn(appointments);
        when(appointmentResponseMapper.entityListToResponseModelList(appointments)).thenReturn(Collections.emptyList());

        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByAccountId(accountId);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getAllAppointments_should_succeed(){
        String appointmentId = "uuid-appt1";
        String availabilityId = "uuid-avail1";
        String accountId = "uuid-account1";
        String serviceId = "uuid-service1";
        Status status = Status.COMPLETED;
        String location = "Location 1";

        Appointment appointment = new Appointment();
        appointment.getAppointmentIdentifier().setAppointmentId(appointmentId);
        List<Appointment> appointments = Collections.singletonList(appointment);

        when(appointmentRepository.findAllAppointmentsByAppointmentIdentifier_AppointmentIdStartingWith(appointmentId)).thenReturn(appointments);
        when(appointmentRepository.findAllAppointmentsByStatus(status)).thenReturn(appointments);
        when(appointmentRepository.findAllAppointmentsByAccountIdStartingWith(accountId)).thenReturn(appointments);

        AppointmentResponseModel responseModel = new AppointmentResponseModel(null, availabilityId, accountId, serviceId, status, location,"","","","");
        List<AppointmentResponseModel> responseModels = Collections.singletonList(responseModel);
        when(appointmentResponseMapper.entityListToResponseModelList(appointments)).thenReturn(responseModels);

        List<AppointmentResponseModel> result = appointmentService.getAllAppointments(appointmentId, null, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());
        assertEquals(responseModels.get(0).getAvailabilityId(), result.get(0).getAvailabilityId());
        assertEquals(responseModels.get(0).getAccountId(), result.get(0).getAccountId());
        assertEquals(responseModels.get(0).getServiceId(), result.get(0).getServiceId());
        assertEquals(responseModels.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(responseModels.get(0).getLocation(), result.get(0).getLocation());

        result = appointmentService.getAllAppointments(null, accountId, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());
        assertEquals(responseModels.get(0).getAvailabilityId(), result.get(0).getAvailabilityId());
        assertEquals(responseModels.get(0).getAccountId(), result.get(0).getAccountId());
        assertEquals(responseModels.get(0).getServiceId(), result.get(0).getServiceId());
        assertEquals(responseModels.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(responseModels.get(0).getLocation(), result.get(0).getLocation());

        result = appointmentService.getAllAppointments(null, null, status.toString());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());
        assertEquals(responseModels.get(0).getAvailabilityId(), result.get(0).getAvailabilityId());
        assertEquals(responseModels.get(0).getAccountId(), result.get(0).getAccountId());
        assertEquals(responseModels.get(0).getServiceId(), result.get(0).getServiceId());
        assertEquals(responseModels.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(responseModels.get(0).getLocation(), result.get(0).getLocation());

        result = appointmentService.getAllAppointments(null, null, null);
    }

    @Test
    void updateAppointmentStatus_should_succeed(){
        String appointmentId = "uuid-appt1";
        String status = "AnyStatus";

        Appointment appointment = new Appointment();
        appointment.getAppointmentIdentifier().setAppointmentId(appointmentId);

        AppointmentResponseModel responseModel = new AppointmentResponseModel(
                "uuid-appt1",
                "uuid-avail1",
                "uuid-account1",
                "uuid-service1",
                Status.CANCELLED,
                "Location 1",
                "",
                "",
                "",
                ""
        );

        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);
        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(responseModel);

        AppointmentResponseModel result = appointmentService.updateAppointmentStatus(appointmentId, status);

        assertEquals(Status.CANCELLED, result.getStatus());
    }


}

