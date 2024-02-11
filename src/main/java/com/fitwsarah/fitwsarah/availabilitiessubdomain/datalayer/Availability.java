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

    private String dayOfWeek;
    private String time;


    public Availability(){
        this.availabilityIdentifier = new AvailabilityIdentifier();
    }


    public Availability(String dayOfWeek, String time) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

}
