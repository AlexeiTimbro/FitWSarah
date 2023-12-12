package com.fitwsarah.fitwsarah.feeedbacksubdomain.datalayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="feebackThreads")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private FeedbackIdentifier feedbackIdentifier;


    @Embedded
    private AccountIdentifier accountIdentifier;
    private Integer stars;
    private String content;


    Feedback(){
        this.feedbackIdentifier = new FeedbackIdentifier();
    }

    public Feedback( AccountIdentifier accountIdentifier, Integer stars, String content) {
        this.feedbackIdentifier = new FeedbackIdentifier();
        this.accountIdentifier = accountIdentifier;
        this.stars = stars;
        this.content = content;
    }
}
