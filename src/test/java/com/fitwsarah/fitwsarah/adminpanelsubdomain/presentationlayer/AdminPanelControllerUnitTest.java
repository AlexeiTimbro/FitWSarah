package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.businesslayer.AdminPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = AdminPanelController.class)
class AdminPanelControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    AdminPanelService adminPanelService;
}