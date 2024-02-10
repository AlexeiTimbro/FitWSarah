package com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentService;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AppointmentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;


    AppointmentResponseModel appointment1 = new AppointmentResponseModel("uuid-appt1", "uuid-avail1", "uuid-account1",  "uuid-service1", Status.COMPLETED, "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");

    private String testToken = "Bearer ";
    private List<AppointmentResponseModel> appointmentResponseModelList;
    @BeforeEach
    void setUp() throws Exception {
        appointmentResponseModelList = Arrays.asList(appointment1);


        given(appointmentService.getAllAppointments(null, null, null)).willReturn(appointmentResponseModelList);
        given(appointmentService.getAppointmentByAppointmentId(appointment1.getAppointmentId()))
                .willReturn(appointment1);

        testToken += obtainAuthToken();
    }


    @Test
    void getAppointmentByAppointmentId_Should_Return_Unauthorized() throws Exception {
        String actualAppointmentId = appointment1.getAppointmentId();
        String token = "invalid_token";

        mockMvc.perform(get("/api/v1/appointments/{appointmentId}", actualAppointmentId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllAppointments_should_succeed() throws Exception {
        mockMvc.perform(get("/api/v1/appointments")
                .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateAppointmentStatus_should_succeed() throws Exception {
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}/cancelled", appointment1.getAppointmentId())
                        .content("COMPLETED")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addAppointment_shouldSucceed() throws Exception{
        String status = "REQUESTED";
        AppointmentRequestModel requestModel = new AppointmentRequestModel("uuid-avail1", "uuid-account1",  "uuid-service1",Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");
        mockMvc.perform(post("/api/v1/appointments")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateAppointmentDetails_shouldSucceed() throws Exception{
        String status = "REQUESTED";
        AppointmentRequestModel requestModel = new AppointmentRequestModel("uuid-avail1", "uuid-account1",  "uuid-service1",Status.valueOf(status), "Location 1", "John", "Smith", "444-444-444","2023-03-20","10:00");
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}", appointment1.getAppointmentId())
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    public String obtainAuthToken() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{"
                + "\"client_id\": \"3UMAwvOXQsi1UiaRq9eM3gQubsMOcOYt\","
                + "\"client_secret\": \"ASrwPK-aY36ZxvVI35m70JbztjZLxfn8LRnWd3z5LuATj8HcnWnze_yaZ-sSW-x9\","
                + "\"audience\": \"https://dev-twa7h1nv0usycyum.us.auth0.com/api/v2/\","
                + "\"grant_type\": \"client_credentials\""
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://dev-twa7h1nv0usycyum.us.auth0.com/oauth/token", entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        return rootNode.path("access_token").asText();
    }
}