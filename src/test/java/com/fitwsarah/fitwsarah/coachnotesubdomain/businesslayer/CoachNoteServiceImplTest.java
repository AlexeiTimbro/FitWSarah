package com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentRequestModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer.CoachNoteServiceImpl;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNote;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNoteRepository;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteRequestMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteResponseMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteRequestModel;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class CoachNoteServiceImplTest {

    @InjectMocks
    private CoachNoteServiceImpl coachNoteService;

    @Mock
    private CoachNoteRepository coachNoteRepository;

    @Mock
    private CoachNoteRequestMapper coachNoteRequestMapper;

    @Mock
    private CoachNoteResponseMapper coachNoteResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCoachNoteByUserIdReturnsExpectedResult() {
        String userId = "testUserId";
        List<CoachNoteResponseModel> expectedResponse = Collections.singletonList(new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda"));
        when(coachNoteRepository.findCoachNoteByUserId(userId)).thenReturn(Collections.emptyList());
        when(coachNoteResponseMapper.entityListToResponseModelList(Collections.emptyList())).thenReturn(expectedResponse);

        List<CoachNoteResponseModel> actualResponse = coachNoteService.getCoachNoteByUserId(userId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getCoachNoteByUserIdReturnsEmptyListWhenNoNotesFound() {
        String userId = "testUserId";
        when(coachNoteRepository.findCoachNoteByUserId(userId)).thenReturn(Collections.emptyList());
        when(coachNoteResponseMapper.entityListToResponseModelList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<CoachNoteResponseModel> actualResponse = coachNoteService.getCoachNoteByUserId(userId);

        assertEquals(Collections.emptyList(), actualResponse);
    }

    @Test
    public void getCoachNoteByUserIdThrowsExceptionWhenUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> coachNoteService.getCoachNoteByUserId(null));
    }


    @Test
    public void getAllCoachNotesReturnsExpectedResult() {
        List<CoachNoteResponseModel> expectedResponse = Collections.singletonList(new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda"));
        when(coachNoteRepository.findAll()).thenReturn(Collections.emptyList());
        when(coachNoteResponseMapper.entityListToResponseModelList(Collections.emptyList())).thenReturn(expectedResponse);

        List<CoachNoteResponseModel> actualResponse = coachNoteService.getAllCoachNotes();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void addCoachNoteReturnsExpectedResult() {

        CoachNoteRequestModel requestModel = new CoachNoteRequestModel("testUserId", "testNote", "test","sdasda","asdasda");

        CoachNote entity = mock(CoachNote.class);
        CoachNoteResponseModel mockedResponse = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");
        when(coachNoteResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(coachNoteRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(coachNoteRepository.save(entity)).thenReturn(entity);

        CoachNoteResponseModel result = coachNoteService.addCoachNote(requestModel);
        assertNotNull(result);
        assertNotNull(result.getCoachNoteId());
        assertNotNull(result.getUserId());
        assertNotNull(result.getAccountId());
        assertNotNull(result.getUsername());
        assertNotNull(result.getContent_EN());
        assertNotNull(result.getContent_FR());

    }

    @Test
    public void updateCoachNoteReturnsExpectedResult() {

        CoachNoteRequestModel requestModel = new CoachNoteRequestModel("testUserId", "testNote", "test","sdasda","asdasda");

        CoachNote entity = mock(CoachNote.class);
        CoachNoteResponseModel mockedResponse = new CoachNoteResponseModel("sadsads","testUserId", "testNote", "test","sdasda","asdasda");
        when(coachNoteResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(coachNoteRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(coachNoteRepository.save(entity)).thenReturn(entity);

        CoachNoteResponseModel result = coachNoteService.updateCoachNoteById(mockedResponse.getCoachNoteId(),requestModel);
        assertNotNull(result);
        assertNotNull(result.getCoachNoteId());
        assertNotNull(result.getUserId());
        assertNotNull(result.getAccountId());
        assertNotNull(result.getUsername());
        assertNotNull(result.getContent_EN());
        assertNotNull(result.getContent_FR());

    }

}