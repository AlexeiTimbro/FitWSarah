package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FitnessPackageControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private String testToken = "Bearer ";

    @MockBean
    private FitnessPackageService fitnessPackageService;

    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1","One On One Training", "1 hour", "Desc", "s",100.00);

    private List<FitnessPackageResponseModel> fitnessPackageList;

    @BeforeEach
    void setUp() throws Exception {
        fitnessPackageList = Arrays.asList(fitnessPackage);
        given(fitnessPackageService.getAllFitnessPackages()).willReturn(fitnessPackageList);
        given(fitnessPackageService.getFitnessPackageByFitnessPackageId(fitnessPackage.getServiceId())).willReturn(fitnessPackage);

        testToken += obtainAuthToken();
    }

    @Test
    void getAllFitnessServices_ShouldReturnFitnessPackages() throws Exception {
        mockMvc.perform(get("/api/v1/fitnessPackages")
                .header("Authorization", testToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }



    @Test
    void getFitnessPackageByFitnessPackageId_ShouldReturnFitnessPackage() throws Exception {
        String actualFitnessPackageId = fitnessPackage.getServiceId();
        mockMvc.perform(get("/api/v1/fitnessPackages/{serviceId}", actualFitnessPackageId)

                        .header("Authorization", testToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.serviceId").value(fitnessPackage.getServiceId())
                );
    }
    @Test
    public void addFitnessPackage_shouldSucceed() throws Exception {
        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel("title","20 minutes","desc","other",22.00);

        mockMvc.perform(post("/api/v1/fitnessPackages")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fitnessPackageRequestModel)))
                .andExpect(status().isCreated());

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