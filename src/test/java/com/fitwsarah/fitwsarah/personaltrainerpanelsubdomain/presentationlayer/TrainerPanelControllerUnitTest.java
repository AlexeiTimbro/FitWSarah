package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.businesslayer.TrainerPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = TrainerPanelController.class)
class TrainerPanelControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    TrainerPanelService trainerPanelService;
}