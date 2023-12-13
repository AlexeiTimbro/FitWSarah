package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentService;
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

    AppointmentResponseModel appointment1 = new AppointmentResponseModel("appointmentID1", "avaiblity6","admin09", "s123", "Desc", "nyc");
    AppointmentResponseModel appointment2 = new AppointmentResponseModel("appointmentID2", "avaiblity6","admin09", "s123", "Desc", "nyc");

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
}


