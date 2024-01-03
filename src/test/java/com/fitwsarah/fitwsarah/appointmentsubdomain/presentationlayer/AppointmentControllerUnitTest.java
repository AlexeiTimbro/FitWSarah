package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentService;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class AppointmentControllerUnitTest {

    @Mock
    AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    AppointmentResponseModel appointment1 = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-admin1", "uuid-service1", Status.COMPLETED, "Location 1");
    AppointmentResponseModel appointment2 = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-admin1", "uuid-service1", Status.COMPLETED, "Location 1");

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
        List<AppointmentResponseModel> expectedAppointments = Arrays.asList(new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-admin1", "uuid-service1", Status.COMPLETED, "Location 1"), new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1", "uuid-admin1", "uuid-service1", Status.COMPLETED, "Location 1"));
        when(appointmentService.getAllAppointmentsByAccountId(accountId)).thenReturn(expectedAppointments);

        List<AppointmentResponseModel> actualAppointments = appointmentController.getAllAppointmentsByAccountId(accountId);

        assertEquals(expectedAppointments, actualAppointments);
    }

    @Test
    void getAllAppointments_should_succeed() {
        // Arrange
        List<AppointmentResponseModel> appointmentResponseModelList = Arrays.asList(
                appointment1,
                appointment2
        );

        when(appointmentService.getAllAppointments()).thenReturn(appointmentResponseModelList);

        // Act
        List<AppointmentResponseModel> result = appointmentController.getAllAppointments();

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
        AppointmentResponseModel expectedResponse = new AppointmentResponseModel(appointmentId, "uuid-avail1", "uuid-account1", "uuid-admin1", "uuid-service1", Status.valueOf(status), "Location 1");

        when(appointmentService.updateAppointmentStatus(appointmentId, status)).thenReturn(expectedResponse);

        // Act
        AppointmentResponseModel result = appointmentController.updateAppointmentStatus(appointmentId, status);

        // Assert
        assertEquals(expectedResponse, result);
    }
}


