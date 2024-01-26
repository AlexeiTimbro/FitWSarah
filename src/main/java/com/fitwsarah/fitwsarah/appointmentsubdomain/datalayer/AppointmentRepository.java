package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment findAppointmentsByAppointmentIdentifier_AppointmentId( String appointmentId);
    List<Appointment> findAllAppointmentByUserIdAndStatus(String userId, Status status);
    List<Appointment> findAllAppointmentsByAppointmentIdentifier_AppointmentIdStartingWith(String appointmentId);
    List<Appointment> findAllAppointmentsByStatus(Status status);
    List<Appointment> findAllAppointmentsByUserIdStartingWith(String userId);
    List<Appointment> findAllAppointmentsByUserId(String userId);


}
