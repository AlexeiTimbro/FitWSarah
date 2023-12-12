package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelIdentifier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

    @DataJpaTest
    class AppointmentRepositoryTest {

        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private AppointmentRepository appointmentRepository;

        @Test
        void givenNewAppointment_whenSave_thenSuccess() {
            // Given
            Appointment newAppointment = new Appointment("availabilityId1", new AdminPanelIdentifier(), "serviceId1", "Scheduled", "Location1");

            // When
            Appointment savedAppointment = appointmentRepository.save(newAppointment);

            // Then
            assertThat(entityManager.find(Appointment.class, savedAppointment.getId())).isEqualTo(newAppointment);
        }

        @Test
        void givenAppointmentCreated_whenFindById_thenSuccess() {
            // Given
            Appointment newAppointment = new Appointment("availabilityId1", new AdminPanelIdentifier(), "serviceId1", "Scheduled", "Location1");

            // When
            // saving the appointmnet to database
            entityManager.persistAndFlush(newAppointment);
            // retreving method from database
            Optional<Appointment> retrievedAppointment = Optional.ofNullable(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(newAppointment.getAppointmentIdentifier().getAppointmentId()));

            // Then
            assertThat(retrievedAppointment).isPresent();
            assertThat(retrievedAppointment.orElse(null)).isEqualTo(newAppointment);
        }
    }


