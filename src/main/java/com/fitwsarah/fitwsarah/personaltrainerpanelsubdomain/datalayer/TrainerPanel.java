package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelIdentifier;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="availabilities")
@Data
public class TrainerPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Embedded
    private TrainerPanelIdentifier trainerPanelIdentifier;

    private String available;
    @Embedded
    private AdminPanelIdentifier adminPanelIdentifier;

    private String date;

    TrainerPanel(){
        this.trainerPanelIdentifier = new TrainerPanelIdentifier();
    }

    public TrainerPanel( String available, AdminPanelIdentifier adminPanelIdentifier, String date) {
        this.trainerPanelIdentifier = new TrainerPanelIdentifier();
        this.available = available;
        this.adminPanelIdentifier = adminPanelIdentifier;
        this.date = date;
    }
}
