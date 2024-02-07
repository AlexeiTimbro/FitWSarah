package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentController;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer.FeedbackService;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.FeedbackRepository;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FeedbackControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    FeedbackService feedbackService;

    private String testToken = "Bearer ";

    FeedbackResponseModel feedback1 = new FeedbackResponseModel("uuid-feed1", "uuid-user1", 3,  "test", State.INVISIBLE);

    private List<FeedbackResponseModel> feedbackResponseModelList;

    @BeforeEach
    void setUp() throws Exception {
        feedbackResponseModelList = Arrays.asList(feedback1);


        given(feedbackService.getAllFeedback(null, null, null)).willReturn(feedbackResponseModelList);
        testToken += obtainAuthToken();
    }

    @Test
    void getAllFeedback_shouldSucceed() throws Exception{
        mockMvc.perform(get("/api/v1/feedbacks")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void addFeedback_shouldSucceed() throws Exception{
        String status = "INVISIBLE";
        FeedbackRequestModel requestModel = new FeedbackRequestModel("user-id1", 3,"test" , State.valueOf(status));
        mockMvc.perform(post("/api/v1/feedbacks")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isCreated());
    }

    @Test
    void removeFeedbackByFeedbackId_ShouldSucceed() throws Exception {
        String actualFeedbackId = "uuid-feed1";
        mockMvc.perform(get("/api/v1/feedbacks/{feedbackId}", actualFeedbackId)
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateFeedbackState_ShouldSucceed() throws Exception{
        mockMvc.perform(patch("/api/v1/feedbacks/{feedbackId}/publish", feedback1.getFeedbackId())
                        .content("VISIBLE")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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


    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}