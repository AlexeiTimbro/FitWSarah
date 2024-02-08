package com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer;


import com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer.FeedbackService;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class FeedbackControllerUnitTest {

    @Mock
    FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    FeedbackResponseModel feedback1 = new FeedbackResponseModel("feed-id1", "uuid-user1", 3,  "test", State.INVISIBLE);
    FeedbackResponseModel feedback2 = new FeedbackResponseModel("feed-id2", "uuid-user2", 5,  "test2", State.INVISIBLE);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllFeedback_ShouldReturnFeedbacks() {
        List<FeedbackResponseModel> expectedFeedbacks = Arrays.asList(feedback1, feedback2);

        when(feedbackService.getAllFeedback(null, null, null)).thenReturn(expectedFeedbacks);

        List<FeedbackResponseModel> actualFeedbacks = feedbackController.getAllFeedbackThreads(null, null, null);
        assertEquals(expectedFeedbacks, actualFeedbacks);
        assertThat(actualFeedbacks.get(0), is(feedback1));
        assertThat(actualFeedbacks.get(1), is(feedback2));
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

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(feedbackService, times(1)).removeFeedback(feedbackId);
    }

    @Test
    void updateFeedbackStatus_shouldSucceed() {
        String feedbackId = "uuid-feed1";
        String status = "VISIBLE";

        FeedbackResponseModel expectedResponse = new FeedbackResponseModel(feedbackId,"user-id1", 3,"test" , State.valueOf(status));
        when(feedbackService.updateFeedbackState(feedbackId, status)).thenReturn(expectedResponse);

        FeedbackResponseModel result = feedbackController.updateFeedbackState(feedbackId, status);

        assertEquals(expectedResponse, result);
    }
}