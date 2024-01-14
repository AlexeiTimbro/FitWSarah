package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="appointments")
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private AppointmentIdentifier appointmentIdentifier;

    //Change this ELIAS WAS DUM AND SHOULD NOT BE NEAR SOCIETY
    //@Embedded
    //private AccountIdentifier accountIdentifier;
    private String userId;

    private String availabilityId;
    private String serviceId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String location;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String date;
    private String time;

    public Appointment() {
        this.appointmentIdentifier = new AppointmentIdentifier();
    }

    public Appointment(String availabilityId, String userId, String serviceId, Status status, String location, String firstName, String lastName, String phoneNum, String date, String time) {
        this.appointmentIdentifier = new AppointmentIdentifier();
        this.availabilityId = availabilityId;
        this.userId = userId;
        this.serviceId = serviceId;
        this.status = status;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.date = date;
        this.time = time;
    }
}
