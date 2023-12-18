package com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = AuthenticationController.class)
class AuthenticationControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;

}