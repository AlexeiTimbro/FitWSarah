package com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class FeedbackRepositoryTest {
    @Autowired
    FeedbackRepository feedbackRepository;

    private String savedFeedbackId;
    Feedback savedFeedback;

    @BeforeEach
    public void setUp() {
        Feedback feedback = new Feedback();
        FeedbackIdentifier identifier = new FeedbackIdentifier();
        identifier.setFeedbackId(savedFeedbackId);
        feedback.setStars(2);
        feedback.setStatus(State.INVISIBLE);
        feedback.setUserId("uuid-user1");
        feedback.setContent("test content");

        savedFeedback = feedbackRepository.save(feedback);
        savedFeedbackId = savedFeedback.getFeedbackIdentifier().getFeedbackId();
    }

    @AfterEach
    public void tearDown() {
        feedbackRepository.deleteAll();
    }


    @Test
    public void whenFindByExistentFeedbackId_thenReturnFeedback() {
        // Arrange
        String mockfeedbackId = "6e83092a-1202-41f1-9d98-1394b52f1d3c";
        Feedback mockFeedback = new Feedback();
        FeedbackIdentifier identifier = new FeedbackIdentifier();
        identifier.setFeedbackId(mockfeedbackId);
        mockFeedback.setFeedbackIdentifier(identifier);

        // Save the mockAppointment in the repository
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