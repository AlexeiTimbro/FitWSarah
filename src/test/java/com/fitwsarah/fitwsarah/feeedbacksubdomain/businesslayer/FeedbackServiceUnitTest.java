package com.fitwsarah.fitwsarah.feeedbacksubdomain.businesslayer;


import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.FeedbackRepository;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer.State;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datamapperlayer.FeedbackRequestMapper;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.datamapperlayer.FeedbackResponseMapper;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackRequestModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class FeedbackServiceUnitTest {

    @InjectMocks
    FeedbackServiceImpl feedbackService;

    @Mock
    FeedbackRepository feedbackRepository;

    @Mock
    FeedbackResponseMapper feedbackResponseMapper;

    @Mock
    FeedbackRequestMapper feedbackRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getAllFeedbacks_shouldSucceed(){
        String status = "INVISIBLE";
        Feedback feedback = new Feedback("Test", 3, "test", State.valueOf(status));
        List<Feedback> feedbacks = Collections.singletonList(feedback);
        when(feedbackRepository.findAll()).thenReturn(feedbacks);

        FeedbackResponseModel responseModel = FeedbackResponseModel.builder()
                .userId("test")
                .content("test")
                .stars(3)
                .status(State.valueOf(status))
                .build();

        List<FeedbackResponseModel> responseModels = Collections.singletonList(responseModel);
        when(feedbackResponseMapper.entityListToResponseModelList(feedbacks)).thenReturn(responseModels);

        List<FeedbackResponseModel> result = feedbackService.getAllFeedback();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getFeedbackId(), result.get(0).getFeedbackId());

    }

    @Test
    public void addFeedback_shouldSucceed(){
        String status = "INVISIBLE";
        FeedbackRequestModel requestModel = new FeedbackRequestModel("uuid-user1", 3,  "test", State.valueOf(status));

        Feedback entity = mock(Feedback.class);
        FeedbackResponseModel mockedResponse = new FeedbackResponseModel("feed-id1", "uuid-user1", 3,  "test", State.valueOf(status));
        when(feedbackResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(feedbackRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(feedbackRepository.save(entity)).thenReturn(entity);

        FeedbackResponseModel result = feedbackService.addFeedback(requestModel);
        assertNotNull(result);
        assertNotNull(result.getFeedbackId());
        assertNotNull(result.getUserId());
        assertNotNull(result.getContent());
        assertNotNull(result.getStatus());
    }

    @Test
    public void removeFeedback_validFeedbackId_shouldSucceed(){
        String feedbackId = "feed-id1";
        Feedback entity = mock(Feedback.class);

        when(feedbackRepository.findFeedbackByFeedbackIdentifier_FeedbackId(feedbackId)).thenReturn(entity);

        feedbackService.removeFeedback(feedbackId);

        verify(feedbackRepository, times(1)).delete(entity);
    }

    @Test
    public void removeFeedback_invalidFeedbackId_shouldFail(){
        String feedbackId = "invalid-feed-id1";

        when(feedbackRepository.findFeedbackByFeedbackIdentifier_FeedbackId(feedbackId)).thenReturn(null);

        feedbackService.removeFeedback(feedbackId);

        verify(feedbackRepository, never()).delete(any(Feedback.class));
    }

}