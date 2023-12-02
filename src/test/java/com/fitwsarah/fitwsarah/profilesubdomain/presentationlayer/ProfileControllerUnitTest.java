package com.fitwsarah.fitwsarah.profilesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.businesslayer.TrainerPanelService;
import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.presentationlayer.TrainerPanelController;
import com.fitwsarah.fitwsarah.profilesubdomain.businesslayer.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = ProfileController.class)
class ProfileControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    ProfileService profileService;
}