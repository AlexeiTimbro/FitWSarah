package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AppointmentServiceUnitTest {
    @InjectMocks
    AppointmentServiceImpl appointmentService;

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;

    @Mock
    private AppointmentRequestMapper appointmentRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAppointmentByAppointmentId_Should_Succeed() {


        String appointmentId = "uuid-appt1";


        Appointment appointment = new Appointment();
        appointment.getAppointmentIdentifier().setAppointmentId(appointmentId);


        AppointmentResponseModel responseModel = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-service1", Status.COMPLETED, "Location 1","John", "Smith", "444-444-444","2023-03-20","10:00");


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

        List<AppointmentResponseModel> responseModels = Arrays.asList(new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00"), new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00"));


        when(appointmentRepository.findAllAppointmentsByUserId(accountId)).thenReturn(appointments);

        when(appointmentResponseMapper.entityListToResponseModelList(appointments)).thenReturn(responseModels);

        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByUserId(accountId);

        assertEquals(responseModels, result);
    }

    @Test
    void getAllAppointmentsByAccountId_returnsEmptyList() {
        String userId = "uuid-account1";
        List<Appointment> appointments = Collections.emptyList();

        when(appointmentRepository.findAllAppointmentsByUserId(userId)).thenReturn(appointments);
        when(appointmentResponseMapper.entityListToResponseModelList(appointments)).thenReturn(Collections.emptyList());

        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByUserId(userId);

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
        when(appointmentRepository.findAllAppointmentsByUserIdStartingWith(accountId)).thenReturn(appointments);

        AppointmentResponseModel responseModel = new AppointmentResponseModel(null, availabilityId, accountId, serviceId, status, location,"","","","","");
        List<AppointmentResponseModel> responseModels = Collections.singletonList(responseModel);
        when(appointmentResponseMapper.entityListToResponseModelList(appointments)).thenReturn(responseModels);

        List<AppointmentResponseModel> result = appointmentService.getAllAppointments(appointmentId, null, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());
        assertEquals(responseModels.get(0).getAvailabilityId(), result.get(0).getAvailabilityId());
        assertEquals(responseModels.get(0).getUserId(), result.get(0).getUserId());
        assertEquals(responseModels.get(0).getServiceId(), result.get(0).getServiceId());
        assertEquals(responseModels.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(responseModels.get(0).getLocation(), result.get(0).getLocation());

        result = appointmentService.getAllAppointments(null, accountId, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());
        assertEquals(responseModels.get(0).getAvailabilityId(), result.get(0).getAvailabilityId());
        assertEquals(responseModels.get(0).getUserId(), result.get(0).getUserId());
        assertEquals(responseModels.get(0).getServiceId(), result.get(0).getServiceId());
        assertEquals(responseModels.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(responseModels.get(0).getLocation(), result.get(0).getLocation());

        result = appointmentService.getAllAppointments(null, null, status.toString());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());
        assertEquals(responseModels.get(0).getAvailabilityId(), result.get(0).getAvailabilityId());
        assertEquals(responseModels.get(0).getUserId(), result.get(0).getUserId());
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
                "John",
                "Smith",
                "455-444-333",
                "2023-03-03",
                "10:00"
        );

        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);
        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(responseModel);

        AppointmentResponseModel result = appointmentService.updateAppointmentStatus(appointmentId, status);

        assertEquals(Status.CANCELLED, result.getStatus());
    }

    @Test
    public void addAppointment_shouldSucceed(){
        String status = "REQUESTED";
        AppointmentRequestModel requestModel = new AppointmentRequestModel("uuid-avail1", "uuid-account1",  "uuid-service1",Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");

        Appointment entity = mock(Appointment.class);
        AppointmentResponseModel mockedResponse = new AppointmentResponseModel("app-id1", "uuid-avail1", "uuid-account1",  "uuid-service1", Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");
        when(appointmentResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(appointmentRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(appointmentRepository.save(entity)).thenReturn(entity);

        AppointmentResponseModel result = appointmentService.addAppointment(requestModel);
        assertNotNull(result);
        assertNotNull(result.getAppointmentId());
        assertNotNull(result.getUserId());
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
    }

    @Test
    public void updateAppointmentDetails_shouldSucceed(){

        String appointmentId = "uuid-appt1";
        AppointmentRequestModel requestModel = new AppointmentRequestModel(
                "uuid-avail1",
                "uuid-account1",
                "uuid-service1",
                Status.COMPLETED,
                "New Location",
                "Jane",
                "Doe",
                "555-555-5555",
                "2023-03-25",
                "12:00"
        );

        Appointment existingAppointment = new Appointment();
        existingAppointment.getAppointmentIdentifier().setAppointmentId(appointmentId);
        existingAppointment.setStatus(Status.SCHEDULED);

        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(existingAppointment);

        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AppointmentResponseModel responseModel = new AppointmentResponseModel(
                appointmentId,
                requestModel.getAvailabilityId(),
                requestModel.getUserId(),
                requestModel.getServiceId(),
                requestModel.getStatus(),
                requestModel.getLocation(),
                requestModel.getFirstName(),
                requestModel.getLastName(),
                requestModel.getPhoneNum(),
                requestModel.getDate(),
                requestModel.getTime()
        );

        when(appointmentResponseMapper.entityToResponseModel(any(Appointment.class)))
                .thenReturn(responseModel);

        AppointmentResponseModel result = appointmentService.updateAppointmentDetails(requestModel, appointmentId);

        assertNotNull(result);
        assertEquals(requestModel.getLocation(), result.getLocation());
        assertEquals(requestModel.getFirstName(), result.getFirstName());
        assertEquals(requestModel.getLastName(), result.getLastName());
        assertEquals(requestModel.getPhoneNum(), result.getPhoneNum());
        assertEquals(requestModel.getDate(), result.getDate());
        assertEquals(requestModel.getTime(), result.getTime());
        assertEquals(requestModel.getStatus(), result.getStatus());

        // Verify if save method was called with updated details
        Mockito.verify(appointmentRepository).save(existingAppointment);
    }

    @Test
    public void updateAppointmentDetails_shouldThrowEntityNotFoundException() {
        // Given
        String nonExistentAppointmentId = "non-existent-id";
        AppointmentRequestModel requestModel = new AppointmentRequestModel(
                "uuid-avail1",
                "uuid-account1",
                "uuid-service1",
                Status.COMPLETED,
                "New Location",
                "Jane",
                "Doe",
                "555-555-5555",
                "2023-03-25",
                "12:00"
        );

        // Simulate repository returning null for a non-existent appointment
        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(nonExistentAppointmentId)).thenReturn(null);

        // Assert that the method throws an EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            // When
            appointmentService.updateAppointmentDetails(requestModel, nonExistentAppointmentId);
        });
    }


    @Test
    public void handleAppointmentRequest_ShouldSucceed(){
        String appointmentId = "uuid-appt1";
        String status = "REQUESTED";

        Appointment appointment = new Appointment();
        appointment.getAppointmentIdentifier().setAppointmentId(appointmentId);

        AppointmentResponseModel responseModel = new AppointmentResponseModel(
                "uuid-appt1",
                "uuid-avail1",
                "uuid-account1",
                "uuid-service1",
                Status.valueOf(status),
                "Location 1",
                "John",
                "Smith",
                "455-444-333",
                "2023-03-03",
                "10:00"
        );

        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);
        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(responseModel);

        AppointmentResponseModel result = appointmentService.handleAppointmentRequest(appointmentId, status);

        assertEquals(Status.valueOf(status), result.getStatus()); }
}

