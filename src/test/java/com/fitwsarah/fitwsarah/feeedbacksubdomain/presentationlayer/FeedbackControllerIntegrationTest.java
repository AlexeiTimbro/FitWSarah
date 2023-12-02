package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:mysql://localhost:0/feebackThreads"})
@AutoConfigureWebTestClient
class FeedbackControllerIntegrationTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    FeedbackRepository feedbackRepository;

}