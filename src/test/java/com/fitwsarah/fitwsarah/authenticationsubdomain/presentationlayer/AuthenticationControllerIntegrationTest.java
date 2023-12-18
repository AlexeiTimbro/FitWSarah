package com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:mysql://localhost:0/authenticaion"})
@AutoConfigureWebTestClient
class AuthenticationControllerIntegrationTest {
    @Autowired
    WebTestClient webTestClient;
}