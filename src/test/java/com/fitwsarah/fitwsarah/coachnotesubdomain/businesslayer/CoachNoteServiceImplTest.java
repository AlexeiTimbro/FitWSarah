package com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.coachnotesubdomain.businesslayer.CoachNoteServiceImpl;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer.CoachNoteRepository;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteRequestMapper;
import com.fitwsarah.fitwsarah.coachnotesubdomain.datamapperlayer.CoachNoteResponseMapper;
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
        List<CoachNoteResponseModel> expectedResponse = Collections.singletonList(new CoachNoteResponseModel("testId", "testUserId", "testNote", "testDate"));
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
}