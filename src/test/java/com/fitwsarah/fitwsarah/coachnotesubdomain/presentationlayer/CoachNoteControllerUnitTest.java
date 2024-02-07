package com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer.CoachNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class CoachNoteControllerUnitTest {

    @Mock
    private CoachNoteService coachNoteService;

    @InjectMocks
    private CoachNoteController coachNoteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCoachNoteByUserIdTest() {
        String userId = "testUserId";
        CoachNoteResponseModel coachNoteResponseModel = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");
        List<CoachNoteResponseModel> expectedResponse = Arrays.asList(coachNoteResponseModel);

        when(coachNoteService.getCoachNoteByUserId(userId)).thenReturn(expectedResponse);

        List<CoachNoteResponseModel> actualResponse = coachNoteController.getCoachNoteByUserId(userId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getAllCoachNotesTest() {
        CoachNoteResponseModel coachNoteResponseModel = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");
        List<CoachNoteResponseModel> expectedResponse = Arrays.asList(coachNoteResponseModel);

        when(coachNoteService.getAllCoachNotes()).thenReturn(expectedResponse);

        List<CoachNoteResponseModel> actualResponse = coachNoteController.getAllCoachNotes();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void addCoachNoteTest() {
        CoachNoteRequestModel coachNoteRequestModel = new CoachNoteRequestModel("testUserId", "testNote", "test","sdasda","asdasda");
        CoachNoteResponseModel expectedResponse = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");

        when(coachNoteService.addCoachNote(coachNoteRequestModel)).thenReturn(expectedResponse);

        CoachNoteResponseModel actualResponse = coachNoteController.addCoachNote(coachNoteRequestModel);

        assertEquals(expectedResponse, actualResponse);
    }
}
