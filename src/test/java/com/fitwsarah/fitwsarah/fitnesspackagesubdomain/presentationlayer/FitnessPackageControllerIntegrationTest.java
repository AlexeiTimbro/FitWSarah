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

    private List<FitnessPackageResponseModel> fitnessPackageList;
    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1","One On One Training", "1 hour", "Desc", 100.00);
    FitnessPackageResponseModel fitnessPackage2 = new FitnessPackageResponseModel("serviceID2", "promoID2", "One On One Training1", "2 hour", "Desc2", 200.00);

    @BeforeEach
    void setUp() {
        fitnessPackageList = Arrays.asList(
                fitnessPackage,
                fitnessPackage2
        );
        given(fitnessPackageService.getAllFitnessPackages()).willReturn(fitnessPackageList);
    }

    @Test
    void getAllFitnessServices_ShouldReturnFitnessPackages() throws Exception {
        mockMvc.perform(get("/api/v1/fitnessPackages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(fitnessPackageList.size()));
    }
}