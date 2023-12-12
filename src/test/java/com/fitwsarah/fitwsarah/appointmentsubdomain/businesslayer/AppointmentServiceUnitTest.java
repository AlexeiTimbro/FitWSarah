package com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


        AppointmentResponseModel responseModel = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-admin1", "uuid-service1", "Scheduled", "Location 1");


        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);


        // Use the appointmentResponseMapper mock to return the responseModel when mapping the appointment to response model

        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(responseModel);


        AppointmentResponseModel result = appointmentService.getAppointmentByAppointmentId(appointmentId);


        assertEquals(appointmentId, result.getAppointmentId());
    }
}

