package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = FeedbackController.class)
class FeedbackControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    FeedbackService feedbackService;

}