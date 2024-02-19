package com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoachNoteTest {

    @Test
    void testCoachNoteConstructor() {
        String accountId = "testAccountId";
        String userId = "testUserId";
        String username = "testUsername";
        String content_EN = "testContentEN";
        String content_FR = "testContentFR";

        CoachNote coachNote = new CoachNote(accountId, userId, username, content_EN, content_FR);

        assertNotNull(coachNote.getCoachNoteIdentifier());
        assertEquals(accountId, coachNote.getAccountId());
        assertEquals(userId, coachNote.getUserId());
        assertEquals(username, coachNote.getUsername());
        assertEquals(content_EN, coachNote.getContent_EN());
        assertEquals(content_FR, coachNote.getContent_FR());
    }

    @Test
    void testCoachNoteEmptyConstructor() {
        CoachNote coachNote = new CoachNote();

        assertNotNull(coachNote.getCoachNoteIdentifier());
    }
}