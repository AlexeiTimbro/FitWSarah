package com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer;



import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class FeedbackRepositoryTest {
    @Autowired
    FeedbackRepository feedbackRepository;

    private String savedFeedbackId;
    private String savedUserId;
    Feedback savedFeedback;

    @BeforeEach
    public void setUp() {
        Feedback feedback = new Feedback();
        FeedbackIdentifier identifier = new FeedbackIdentifier();
        identifier.setFeedbackId(savedFeedbackId);
        feedback.setStars(2);
        feedback.setStatus(State.INVISIBLE);
        feedback.setUserId(savedUserId);
        feedback.setContent("test content");

        savedUserId = "uuid-user1";
        savedFeedback = feedbackRepository.save(feedback);
        savedFeedbackId = savedFeedback.getFeedbackIdentifier().getFeedbackId();
    }

    @AfterEach
    public void tearDown() {
        feedbackRepository.deleteAll();
    }

    @Test
    void findAllFeedbacksByFeedbackIdentifier_FeedbackIdStartingWith_Should_Return_Correct_Appointments() {
        List<Feedback> result = feedbackRepository.findAllFeedbacksByFeedbackIdentifier_FeedbackIdStartingWith(savedFeedbackId);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(feedback -> feedback.getFeedbackIdentifier().getFeedbackId().equals(savedFeedbackId)));
    }
    @Test
    void findAllFeedbacksByUserId_StartingWith_Should_Return_Correct_Appointments() {
        List<Feedback> result = feedbackRepository.findAllFeedbacksByUserIdStartingWith(savedUserId);

        assertEquals(0, result.size());
        assertTrue(result.stream().allMatch(feedback -> feedback.getUserId().equals(savedUserId)));
    }

    @Test
    void findAllFeedbacksByState_StartingWith_Should_Return_Correct_Appointments() {
        List<Feedback> result = feedbackRepository.findAllFeedbacksByStatus(State.INVISIBLE);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(feedback -> feedback.getStatus().equals(State.INVISIBLE)));
    }

    @Test
    public void whenFindByExistentFeedbackId_thenReturnFeedback() {
        // Arrange
        String mockfeedbackId = "6e83092a-1202-41f1-9d98-1394b52f1d3c";
        Feedback mockFeedback = new Feedback();
        FeedbackIdentifier identifier = new FeedbackIdentifier();
        identifier.setFeedbackId(mockfeedbackId);
        mockFeedback.setFeedbackIdentifier(identifier);
        feedbackRepository.save(mockFeedback);

        // Act
        Feedback found = feedbackRepository.findFeedbackByFeedbackIdentifier_FeedbackId(mockfeedbackId);

        // Assert
        assertNotNull(found);
        assertEquals(mockfeedbackId, found.getFeedbackIdentifier().getFeedbackId());
    }
    @Test
    public void whenFindByNonExistentFeedbackId_thenReturnNull() {
        // Arrange
        String nonExistentFeedbackId = "nonExistentId";

        // Act
        Feedback found = feedbackRepository.findFeedbackByFeedbackIdentifier_FeedbackId(nonExistentFeedbackId);

        // Assert
        assertNull(found);
    }


}