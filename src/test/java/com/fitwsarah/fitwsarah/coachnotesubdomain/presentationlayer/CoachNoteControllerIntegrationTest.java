package com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer.CoachNoteService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CoachNoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CoachNoteService coachNoteService;
    private CoachNoteResponseModel coachNoteResponseModel = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");
    private String testToken = "Bearer ";
    private List<CoachNoteResponseModel> coachNoteResponseModelList;
    private String testUserId = "testUserId";

    @BeforeEach
    void setUp() throws Exception {
        coachNoteResponseModelList = Arrays.asList(coachNoteResponseModel);


        given(coachNoteService.getCoachNoteByUserId(testUserId)).willReturn(coachNoteResponseModelList);

        testToken += obtainAuthToken();
    }

    @Test
    public void getCoachNoteByUserIdTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coachnotes/users/" + testUserId)
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCoachNotesTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coachnotes")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addCoachNoteTest() throws Exception {
        mockMvc.perform(post("/api/v1/coachnotes")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CoachNoteRequestModel("testAccountId", "testUserId", "testUsername", "testContent_EN", "testContent_FR"))))
                .andExpect(status().isOk());
    }
    @Test
    public void updateCoachNoteByIdTest() throws Exception {
        String coachNoteId = "testCoachNoteId";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/coachnotes/" + coachNoteId)
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CoachNoteRequestModel("testAccountId", "testUserId", "testUsername", "testContent_EN", "testContent_FR"))))
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
