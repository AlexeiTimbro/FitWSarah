package com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:mysql://localhost:0/adminAccounts"})
@AutoConfigureWebTestClient
class AdminPanelControllerIntegrationTest {
@Autowired
    WebTestClient webTestClient;
  @Autowired
    AdminPanelRepository adminPanelRepository;
}