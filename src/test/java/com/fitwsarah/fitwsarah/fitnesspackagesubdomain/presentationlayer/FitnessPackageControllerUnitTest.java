package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = FitnessPackageController.class)
class FitnessPackageControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    FitnessPackageService fitnessPackageService;

}