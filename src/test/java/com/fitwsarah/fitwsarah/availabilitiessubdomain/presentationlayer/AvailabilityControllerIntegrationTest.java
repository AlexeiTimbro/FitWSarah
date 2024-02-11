package com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer.AvailabilityService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AvailabilityControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AvailabilityService availabilityService;

    private String dayOfWeek = "Monday";
    AvailabilityResponseModel availability1 = new AvailabilityResponseModel("uuid-1", dayOfWeek, "10:00");

    private List<AvailabilityResponseModel> availabilityResponseModels;

    private String testToken = "Bearer ";

    @BeforeEach
    void setUp() throws Exception {
        availabilityResponseModels = Arrays.asList(availability1);


        given(availabilityService.getAllAvailabilities(dayOfWeek)).willReturn(availabilityResponseModels);
        testToken += obtainAuthToken();
    }


    @Test
    void getAllAvailabilities_shouldSucceed() throws Exception {
            mockMvc.perform(get("/api/v1/availabilities?dayOfWeek="+dayOfWeek)
                            .header("Authorization", testToken)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
    }

    @Test
    void addAvailability() throws Exception{
        AvailabilityRequestModel requestModel = new AvailabilityRequestModel("10:00", dayOfWeek);
        mockMvc.perform(post("/api/v1/availabilities/add?dayOfWeek="+dayOfWeek)
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteAvailability() throws Exception{
        String actualAvailabilityId = "uuid-1";
        mockMvc.perform(delete("/api/v1/feedbacks/{availabilityId}", actualAvailabilityId)
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