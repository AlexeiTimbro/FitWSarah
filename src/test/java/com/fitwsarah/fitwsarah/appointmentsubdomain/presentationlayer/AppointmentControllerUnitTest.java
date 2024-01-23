package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentService;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


class AppointmentControllerUnitTest {

    @Mock
    AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;


    AppointmentResponseModel appointment1 = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1",  "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");
    AppointmentResponseModel appointment2 = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1",  "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAppointmentById_ShouldReturnAppointment() {

        // Arrange
        List<AppointmentResponseModel> appointmentResponseModelList = Arrays.asList(
                appointment1,
                appointment2
        );

        when(appointmentService.getAppointmentByAppointmentId(anyString())).thenReturn(appointment1);

        AppointmentResponseModel result = appointmentController.getAppointmentById(appointment1.getAppointmentId());

        // Assert
        assertThat(result, is(appointment1));
    }

    @Test
    public void getAllAppointmentsByAccountId_ShouldReturnAppointments() {
        String accountId = "testAccountId";

        List<AppointmentResponseModel> expectedAppointments = Arrays.asList(new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1",  "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00"), new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1",  "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00"));

        when(appointmentService.getAllAppointmentsByUserId(accountId)).thenReturn(expectedAppointments);

        List<AppointmentResponseModel> actualAppointments = appointmentController.getAllAppointmentsByUserId(accountId);

        assertEquals(expectedAppointments, actualAppointments);
    }

    @Test
    void getAllAppointments_should_succeed() {
        // Arrange
        List<AppointmentResponseModel> appointmentResponseModelList = Arrays.asList(
                appointment1,
                appointment2
        );

        when(appointmentService.getAllAppointments(null, null, null)).thenReturn(appointmentResponseModelList);

        // Act
        List<AppointmentResponseModel> result = appointmentController.getAllAppointments(null, null, null);

        // Assert
        assertThat(result, hasSize(2));
        assertThat(result.get(0), is(appointment1));
        assertThat(result.get(1), is(appointment2));
    }

    @Test
    void updateAppointmentStatus_should_succeed() {
        // Arrange
        String appointmentId = "uuid-appt1";
        String status = "COMPLETED";

        AppointmentResponseModel expectedResponse = new AppointmentResponseModel(appointmentId, "uuid-avail1", "uuid-account1",  "uuid-service1", Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");

        when(appointmentService.updateAppointmentStatus(appointmentId)).thenReturn(expectedResponse);

        // Act
        AppointmentResponseModel result = appointmentController.updateAppointmentStatus(appointmentId);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    void addAppointment_shouldSucceed(){
        String appointmentId = "uuid-appt1";
        String status = "REQUESTED";
        AppointmentRequestModel requestModel = new AppointmentRequestModel("uuid-avail1", "uuid-account1",  "uuid-service1",Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");
        AppointmentResponseModel expectedResponse = new AppointmentResponseModel(appointmentId, "uuid-avail1", "uuid-account1",  "uuid-service1", Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");
        when(appointmentService.addAppointment(requestModel)).thenReturn(expectedResponse);

        ResponseEntity<AppointmentResponseModel> result = appointmentController.addAppointment(requestModel);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }

    @Test
    void updateAppointmentById_shouldSucceed() {
        // Arrange
        String appointmentId = "uuid-appt1";
        AppointmentRequestModel requestModel = new AppointmentRequestModel("uuid-avail1", "uuid-account1", "uuid-service1", Status.REQUESTED, "Location 1", "John", "Smith", "444-444-4444", "2023-03-20", "10:00");
        AppointmentResponseModel expectedResponse = new AppointmentResponseModel(appointmentId, "uuid-avail1", "uuid-account1", "uuid-service1", Status.REQUESTED, "Location 1", "John", "Smith", "444-444-4444", "2023-03-20", "10:00");

        when(appointmentService.updateAppointmentDetails(eq(requestModel), eq(appointmentId))).thenReturn(expectedResponse);

        // Act
        AppointmentResponseModel result = appointmentController.updateAppointmentById(requestModel, appointmentId);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    void updateAppointmentById_shouldThrowExceptionWhenAppointmentNotFound() {
        // Arrange
        String appointmentId = "uuid-nonexistent";
        AppointmentRequestModel requestModel = new AppointmentRequestModel("uuid-avail1", "uuid-account1", "uuid-service1", Status.REQUESTED, "Location 1", "John", "Smith", "444-444-4444", "2023-03-20", "10:00");

        when(appointmentService.updateAppointmentDetails(eq(requestModel), eq(appointmentId))).thenThrow(new EntityNotFoundException("Appointment with ID " + appointmentId + " not found"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> appointmentController.updateAppointmentById(requestModel, appointmentId));
    }
}


