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
    private String accountId;
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

    public Appointment(String availabilityId, String accountId, String serviceId, Status status, String location, String firstName, String lastName, String phoneNum, String date, String time) {
        this.appointmentIdentifier = new AppointmentIdentifier();
        this.availabilityId = availabilityId;
        this.accountId = accountId;
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
