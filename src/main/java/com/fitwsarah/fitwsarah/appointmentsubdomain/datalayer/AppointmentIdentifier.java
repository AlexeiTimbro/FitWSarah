package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AppointmentIdentifier {
    private String appointmentId;

    public AppointmentIdentifier(){
        this.appointmentId = UUID.randomUUID().toString();
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
