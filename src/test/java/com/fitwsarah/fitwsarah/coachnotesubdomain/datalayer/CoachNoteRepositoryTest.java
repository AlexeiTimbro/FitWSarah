package com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class CoachNoteRepositoryTest {

    @Autowired
    private CoachNoteRepository coachNoteRepository;

    private List<CoachNote> savedCoachNotes;

    @BeforeEach
    public void setUp() {
        CoachNote coachNote1 = new CoachNote();
        coachNote1.setUserId("existingUserId");
        coachNote1.setCoachNoteIdentifier(new CoachNoteIdentifier());
        coachNote1.setContent_EN("testContent1");
        coachNote1.setContent_FR("testContent1");

        CoachNote coachNote2 = new CoachNote();
        coachNote2.setUserId("existingUserId");
        coachNote2.setCoachNoteIdentifier(new CoachNoteIdentifier());
        coachNote2.setContent_EN("testContent2");
        coachNote2.setContent_FR("testContent2");

        savedCoachNotes = coachNoteRepository.saveAll(Arrays.asList(coachNote1, coachNote2));
    }

    @Test
    public void findCoachNoteByUserIdReturnsExpectedResultWhenNotesExist() {
        String userId = "existingUserId";
        List<CoachNote> actualNotes = coachNoteRepository.findCoachNoteByUserId(userId);

        assertEquals(savedCoachNotes, actualNotes);
    }

    @Test
    public void findCoachNoteByUserIdReturnsEmptyListWhenNoNotesExist() {
        String userId = "nonExistingUserId";
        coachNoteRepository.deleteAll();

        List<CoachNote> actualNotes = coachNoteRepository.findCoachNoteByUserId(userId);

        assertEquals(Collections.emptyList(), actualNotes);
    }

    @Test
    public void findCoachNoteByUserIdReturnsEmptyListWhenUserIdIsNull() {
        String userId = null;
        List<CoachNote> actualNotes = coachNoteRepository.findCoachNoteByUserId(userId);
        assertEquals(Collections.emptyList(), actualNotes);
    }
}