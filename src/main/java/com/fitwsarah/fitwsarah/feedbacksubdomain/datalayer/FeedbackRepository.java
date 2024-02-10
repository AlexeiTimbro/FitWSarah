package com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer;



import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findFeedbackByFeedbackIdentifier_FeedbackId(String feedbackId);

    List<Feedback> findAllFeedbackByUserIdAndStatus(String userId, Status status);
    List<Feedback> findAllFeedbacksByFeedbackIdentifier_FeedbackIdStartingWith(String feedbackId);
    List<Feedback> findAllFeedbacksByStatus(State status);
    List<Feedback> findAllFeedbacksByUserIdStartingWith(String userId);
}
