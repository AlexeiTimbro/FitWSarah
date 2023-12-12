package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentService;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageController;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    AppointmentResponseModel appointment1 = new AppointmentResponseModel("appointmentID1", "avaiblity6","admin09", "s123", "Desc", "nyc");

    private List<AppointmentResponseModel> appointmentResponseModelList;
    @BeforeEach
    void setUp() {
        appointmentResponseModelList = Arrays.asList(appointment1);


        given(appointmentService.getAllAppointments()).willReturn(appointmentResponseModelList);
        given(appointmentService.getAppointmentByAppointmentId(appointment1.getAppointmentId()))
                .willReturn(appointment1);
    }


    @Test
    void getAppointmentbyAppointmentId_Should_Succed() throws Exception {
        String actualAppointmentId = appointment1.getAppointmentId();
        mockMvc.perform(get("/api/v1/appointments/{appointmentId}", actualAppointmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.appointmentId").value(appointment1.getAppointmentId()));
    }


}