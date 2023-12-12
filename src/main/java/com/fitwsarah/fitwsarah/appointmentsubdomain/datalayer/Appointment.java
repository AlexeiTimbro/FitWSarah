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


    private String availabilityId;

    @Embedded
    private AdminPanelIdentifier adminPanelIdentifier;

    private String serviceId;

    private String status;

    private String location;

    public Appointment(){
        this.appointmentIdentifier = new AppointmentIdentifier();
    }


    // Add this constructor in your Appointment class

    public Appointment( String availabilityId,AdminPanelIdentifier adminPanelIdentifier,  String serviceId, String status, String location) {

        this.appointmentIdentifier = new AppointmentIdentifier();
        this.adminPanelIdentifier = adminPanelIdentifier;
        this.availabilityId = availabilityId;
        this.serviceId = serviceId;
        this.status = status;
        this.location = location;
    }

}
