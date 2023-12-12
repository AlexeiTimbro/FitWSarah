package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelIdentifier;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageIdentifier;
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
    private AdminPanelIdentifier adminPanelIdentifier;

    @Embedded
    private AccountIdentifier accountIdentifier;

    @Embedded
    private FitnessPackageIdentifier fitnessPackageIdentifier;

    private String status;

    private String location;

    @Embedded
    private Availability availability;

    Appointment(){
        this.appointmentIdentifier = new AppointmentIdentifier();
    }

    public Appointment(AdminPanelIdentifier adminPanelIdentifier, AccountIdentifier accountIdentifier, FitnessPackageIdentifier fitnessPackageIdentifier, String status, String location, Availability availability) {
        this.appointmentIdentifier = new AppointmentIdentifier();
        this.adminPanelIdentifier = adminPanelIdentifier;
        this.accountIdentifier = accountIdentifier;
        this.fitnessPackageIdentifier = fitnessPackageIdentifier;
        this.status = status;
        this.location = location;
        this.availability = availability;
    }
}
