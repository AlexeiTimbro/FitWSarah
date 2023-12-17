package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FitnessPackageController.class)
class FitnessPackageControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FitnessPackageService fitnessPackageService;

    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1","One On One Training", "1 hour", "Desc", "s",100.00);

    private List<FitnessPackageResponseModel> fitnessPackageList;

    @BeforeEach
    void setUp() {
        fitnessPackageList = Arrays.asList(fitnessPackage);
        given(fitnessPackageService.getAllFitnessPackages()).willReturn(fitnessPackageList);
        given(fitnessPackageService.getFitnessPackageByFitnessPackageId(fitnessPackage.getServiceId())).willReturn(fitnessPackage);
    }

    @Test
    void getAllFitnessServices_ShouldReturnFitnessPackages() throws Exception {
        mockMvc.perform(get("/api/v1/fitnessPackages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(fitnessPackageList.size()));
    }

    @Test
    void getFitnessPackageByFitnessPackageId_ShouldReturnFitnessPackage() throws Exception {
        String actualFitnessPackageId = fitnessPackage.getServiceId();
        mockMvc.perform(get("/api/v1/fitnessPackages/{serviceId}", actualFitnessPackageId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.serviceId").value(fitnessPackage.getServiceId())
                );
    }










}