package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebFluxTest(FitnessPackageController.class)
@AutoConfigureWebTestClient
class FitnessPackageControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FitnessPackageService fitnessPackageService;

    private List<FitnessPackageResponseModel> fitnessPackageList;
    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1", "One On One Training", "1 hour", "Desc", 100.00);
    FitnessPackageResponseModel fitnessPackage2 = new FitnessPackageResponseModel("serviceID2", "promoID2", "One On One Training1", "2 hours", "Desc2", 200.00);

    @BeforeEach
    void setUp() {
        fitnessPackageList = Arrays.asList(
                fitnessPackage,
                fitnessPackage2
        );
        given(fitnessPackageService.getAllFitnessPackages()).willReturn(Flux.fromIterable(fitnessPackageList));
    }

    @Test
    void getAllFitnessServices_ShouldReturnFitnessPackages() {
        webTestClient.get()
                .uri("/api/v1/fitnessPackages")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_EVENT_STREAM)
                .expectBodyList(FitnessPackageResponseModel.class)
                .hasSize(fitnessPackageList.size());

    }
}
