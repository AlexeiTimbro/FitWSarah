package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;


import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanelIdentifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private String savedAppointmentId;

    @BeforeEach
    public void setUp() {
        Appointment appointment = new Appointment();
        AppointmentIdentifier identifier = new AppointmentIdentifier();
        appointment.setAppointmentIdentifier(identifier);

        // Save the appointment and get the saved ID
        Appointment savedAppointment = appointmentRepository.save(appointment);
        savedAppointmentId = savedAppointment.getAppointmentIdentifier().getAppointmentId();
    }

    @AfterEach
    public void tearDown() {
        // Delete all appointments after each test
        appointmentRepository.deleteAll();
    }

    @Test
    public void whenFindByCustomerId_thenReturnAppointment() {
        // Arrange
        assertNotNull(savedAppointmentId);

        // Act
        Appointment found = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(savedAppointmentId);

        // Assert
        assertNotNull(found);
        assertEquals(savedAppointmentId, found.getAppointmentIdentifier().getAppointmentId());
    }

    @Test
    public void whenFindByNonExistentCustomerId_thenReturnNull() {
        // Arrange
        String nonExistentAppointmentId = "nonExistentId";

        // Act
        Appointment found = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(nonExistentAppointmentId);

        // Assert
        assertNull(found);
    }
}
