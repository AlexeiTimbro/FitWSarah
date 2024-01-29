package com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer;


import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findFeedbackByFeedbackIdentifier_FeedbackId(String feedbackId);

}
