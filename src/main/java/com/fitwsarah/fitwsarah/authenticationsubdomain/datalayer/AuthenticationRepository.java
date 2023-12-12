package com.fitwsarah.fitwsarah.authenticationsubdomain.datalayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface AuthenticationRepository /*extends JpaRepository<Authentication, Integer>*/ {
}
