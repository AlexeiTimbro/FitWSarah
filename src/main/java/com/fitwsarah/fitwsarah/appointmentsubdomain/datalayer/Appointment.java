package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelIdentifier;
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
    @Embedded
    private AccountIdentifier accountIdentifier;
    private String availabilityId;
    @Embedded
    private AdminPanelIdentifier adminPanelIdentifier;

    private String serviceId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String location;

    public Appointment(){
        this.appointmentIdentifier = new AppointmentIdentifier();
    }


    // Add this constructor in your Appointment class

    public Appointment( String availabilityId,AdminPanelIdentifier adminPanelIdentifier,  String serviceId, Status status, String location) {

        this.appointmentIdentifier = new AppointmentIdentifier();
        this.adminPanelIdentifier = adminPanelIdentifier;
        this.availabilityId = availabilityId;
        this.serviceId = serviceId;
        this.status = status;
        this.location = location;
    }

}
