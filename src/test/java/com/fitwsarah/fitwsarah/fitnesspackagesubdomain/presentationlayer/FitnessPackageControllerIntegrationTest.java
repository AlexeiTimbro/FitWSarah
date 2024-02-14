package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.Status;
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
class FitnessPackageControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private String testToken = "Bearer ";

    @MockBean
    private FitnessPackageService fitnessPackageService;

    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1", Status.INVISIBLE, "1 hour", "Desc", "s", "fsdaf", "fsaf", "fsadf", "fsaf", 22.00);

    private List<FitnessPackageResponseModel> fitnessPackageList;

   /* @BeforeEach
    void setUp() throws Exception {
        fitnessPackageList = Arrays.asList(fitnessPackage);
        given(fitnessPackageService.getAllFitnessPackages()).willReturn(fitnessPackageList);
        given(fitnessPackageService.getFitnessPackageByFitnessPackageId(fitnessPackage.getServiceId())).willReturn(fitnessPackage);

        testToken += obtainAuthToken();
    }
*/
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
        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel(Status.INVISIBLE,"20 minutes","desc","other","fdsaf", "fdsaf", "fasdf", "22.00", 99.0 );

        mockMvc.perform(post("/api/v1/fitnessPackages")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fitnessPackageRequestModel)))
                .andExpect(status().isCreated());

    }


    @Test
    void updateFitnessPackage_shouldSucceed() throws Exception{

        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel(Status.INVISIBLE,"20 minutes","desc","other","fdsaf", "fdsaf", "fasdf", "22.00", 99.0 );
        mockMvc.perform(put("/api/v1/fitnessPackages/{serviceId}", fitnessPackage.getServiceId())
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(fitnessPackageRequestModel)))
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