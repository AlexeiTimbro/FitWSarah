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
import static org.mockito.Mockito.*;

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

    @Test
    public void updateCoachNoteTest() {
        String coachNoteId = "sadsads";
        CoachNoteRequestModel coachNoteRequestModel = new CoachNoteRequestModel("testUserId", "testNote", "test","sdasda","asdasda");
        CoachNoteResponseModel expectedResponse = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");

        when(coachNoteService.updateCoachNoteById(coachNoteId, coachNoteRequestModel)).thenReturn(expectedResponse);

        CoachNoteResponseModel actualResponse = coachNoteController.updateCoachNoteById(coachNoteId, coachNoteRequestModel);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void deleteCoachNoteByIdTest() {
        String coachNoteId = "sadsads";

        // Call the method under test
        coachNoteController.deleteCoachNoteById(coachNoteId);

        // Verify that the service method was called once with the correct id
        verify(coachNoteService, times(1)).deleteCoachNoteById(coachNoteId);
    }

    @Test
    public void deleteCoachNoteByIdReturnExceptionNotFoundTest(){
            String coachNoteId = "sadsads";
        doThrow(new RuntimeException("CoachNote not found")).when(coachNoteService).deleteCoachNoteById(coachNoteId);
        try {
            coachNoteController.deleteCoachNoteById(coachNoteId);
        } catch (RuntimeException e) {
            assertEquals("CoachNote not found", e.getMessage());
        } }
}
