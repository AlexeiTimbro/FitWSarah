package com.fitwsarah.fitwsarah.availabilitiessubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.availabilitiessubdomain.businesslayer.AvailabilityService;
import com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer.Availability;
import com.fitwsarah.fitwsarah.feedbacksubdomain.businesslayer.FeedbackService;
import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.State;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackController;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackRequestModel;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackResponseModel;
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
import static org.mockito.Mockito.times;


@ActiveProfiles("test")
class AvailabilityControllerUnitTest {

    @Mock
    AvailabilityService availabilityService;

    @InjectMocks
    private AvailabilityController availabilityController;

    private String dayOfWeek = "Monday";

    AvailabilityResponseModel availability1 = new AvailabilityResponseModel("uuid-1", dayOfWeek, "10:00");
    AvailabilityResponseModel availability2 = new AvailabilityResponseModel("uuid-2", dayOfWeek, "13:30");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllAvailabilities() {
        List<AvailabilityResponseModel> expectedAvailabilities = Arrays.asList(availability1, availability2);

        when(availabilityService.getAllAvailabilities(dayOfWeek)).thenReturn(expectedAvailabilities);

        List<AvailabilityResponseModel> actualAvailabilities = availabilityController.getAllAvailabilities(dayOfWeek);
        assertEquals(expectedAvailabilities, actualAvailabilities);
        assertThat(actualAvailabilities.get(0), is(availability1));
        assertThat(actualAvailabilities.get(1), is(availability2));
    }

    @Test
    void addAvailability_shouldSucceed() {
        String availabilityId = "uuid-2231";
        AvailabilityRequestModel requestModel = new AvailabilityRequestModel("10:00", dayOfWeek);
        AvailabilityResponseModel expectedResponse = new AvailabilityResponseModel(availabilityId, dayOfWeek, "10:00");
        when(availabilityService.addAvailability(dayOfWeek,requestModel)).thenReturn(expectedResponse);

        ResponseEntity<AvailabilityResponseModel> result = availabilityController.addAvailability(dayOfWeek, requestModel);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }

    @Test
    void deleteAvailability_shouldSucceed() {
        String availabilityId = "uuid-1";
        doNothing().when(availabilityService).deleteAvailability(availabilityId);

        ResponseEntity<Void> result = availabilityController.deleteAvailability(availabilityId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(availabilityService, times(1)).deleteAvailability(availabilityId);
    }
}