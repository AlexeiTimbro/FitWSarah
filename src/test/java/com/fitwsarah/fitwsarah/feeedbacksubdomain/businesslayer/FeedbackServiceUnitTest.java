package com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer;

import com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer.AuthenticationService;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FeedbackServiceUnitTest {
    //@Autowired
    //FeedbackService feedbackService;
    @MockBean
    FeedbackRepository feedbackRepository;



}