package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanel;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment findAppointmentsByAdminPanelIdentifier_AdminIdAndAppointmentIdentifier_AppointmentId(String adminId, String appointmentId);


}
