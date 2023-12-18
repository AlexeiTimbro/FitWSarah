package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanel;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment findAppointmentsByAppointmentIdentifier_AppointmentId( String appointmentId);
    List<Appointment> findAppointmentByAccountIdentifier_AccountId(String accountId);
}
