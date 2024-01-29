package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;


import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer.FeedbackService;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class FeedbackControllerUnitTest {

    @Mock
    FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllAppointmentsByAccountId_ShouldReturnAppointments() {
        List<FeedbackResponseModel> expectedFeedbacks = Arrays.asList(new FeedbackResponseModel("feed-id1", "uuid-user1", 3,  "test", State.INVISIBLE), new FeedbackResponseModel("feed-id2", "uuid-user2", 4,  "test2", State.INVISIBLE));

        when(feedbackService.getAllFeedback()).thenReturn(expectedFeedbacks);

        List<FeedbackResponseModel> actualFeedbacks = feedbackController.getAllFeedbackThreads();
        assertEquals(expectedFeedbacks, actualFeedbacks);
    }
    @Test
    public void addFeedback_shouldSucceed(){
        String feedbackId = "uuid-feed1";
        String status = "INVISIBLE";
        FeedbackRequestModel requestModel = new FeedbackRequestModel("user-id1", 3,"test" , State.valueOf(status));
        FeedbackResponseModel expectedResponse = new FeedbackResponseModel(feedbackId, "user-id1", 3,  "test", State.valueOf(status));
        when(feedbackService.addFeedback(requestModel)).thenReturn(expectedResponse);

        ResponseEntity<FeedbackResponseModel> result = feedbackController.addFeedback(requestModel);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }

    @Test
    public void removeFeedback_validFeedbackId_shouldSucceed() {
        String feedbackId = "uuid-feed1";
        doNothing().when(feedbackService).removeFeedback(feedbackId);

        ResponseEntity<Void> result = feedbackController.deleteFeedbackById(feedbackId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(feedbackService, times(1)).removeFeedback(feedbackId);
    }

}