package com.fitwsarah.fitwsarah.coachnotesubdomain.datalayer;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="coach_notes")
@Data
public class CoachNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private CoachNoteIdentifier coachNoteIdentifier;

    private String userId;

    private String content;

    public CoachNote(){
        this.coachNoteIdentifier = new CoachNoteIdentifier();
    }
    public CoachNote (String userId, String content){
        this.coachNoteIdentifier = new CoachNoteIdentifier();
        this.userId = userId;
        this.content = content;
    }
}
