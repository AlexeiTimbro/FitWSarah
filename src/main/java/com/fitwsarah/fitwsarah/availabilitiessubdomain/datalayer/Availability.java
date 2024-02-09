package com.fitwsarah.fitwsarah.availabilitiessubdomain.datalayer;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="availabilities")
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private AvailabilityIdentifier availabilityIdentifier;

    private String userId;

    private Boolean available;

    private String date;
    private String time;


    public Availability(){
        this.availabilityIdentifier = new AvailabilityIdentifier();
    }


    public Availability(String userId, Boolean available, String date, String time) {
        this.userId = userId;
        this.available = available;
        this.date = date;
        this.time = time;
    }

}
