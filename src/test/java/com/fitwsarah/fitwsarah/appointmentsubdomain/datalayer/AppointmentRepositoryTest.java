package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;
    private String savedAppointmentId;
    Appointment savedAppointment;

    @BeforeEach
    public void setUp() {
        Appointment appointment = new Appointment();
        AppointmentIdentifier identifier = new AppointmentIdentifier();
        identifier.setAppointmentId(savedAppointmentId);
        appointment.setLocation("Location 1");
        appointment.setStatus(Status.COMPLETED);
        appointment.setAvailabilityId("uuid-avail1");
        appointment.setUserId("uuid-account1");
        appointment.setServiceId("uuid-service1");

        savedAppointment = appointmentRepository.save(appointment);
        savedAppointmentId = savedAppointment.getAppointmentIdentifier().getAppointmentId();
    }

    @AfterEach
    public void tearDown() {
        appointmentRepository.deleteAll();
    }

    @Test
    public void whenFindByCustomerId_thenReturnAppointment() {
        // Arrange
        String mockAppointmentId = "6e83092a-1202-41f1-9d98-1394b52f1d3c";
        Appointment mockAppointment = new Appointment();
        AppointmentIdentifier identifier = new AppointmentIdentifier();
        identifier.setAppointmentId(mockAppointmentId);
        mockAppointment.setAppointmentIdentifier(identifier);

        // Save the mockAppointment in the repository
        appointmentRepository.save(mockAppointment);

        // Act
        Appointment found = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(mockAppointmentId);

        // Assert
        assertNotNull(found);
        assertEquals(mockAppointmentId, found.getAppointmentIdentifier().getAppointmentId());
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

    @Test
    void whenFindAppointmentByAccountIdentifier_AccountIdReturnsNonEmptyList() {
        // Act
        Appointment found = appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(savedAppointmentId);
        // Assert
        assertNotNull(found);
        assertEquals(savedAppointmentId, found.getAppointmentIdentifier().getAppointmentId());
    }

    @Test
    public void whenFindAppointmentByNonExistentAccountIdentifier_AccountIdReturnsEmptyList() {
        // Arrange
        String nonExistentAccountId = "nonExistentId";

        // Act
        List<Appointment> result = appointmentRepository.findAllAppointmentsByUserId(nonExistentAccountId);
        assertTrue(result.isEmpty());
    }


    @Test
    void findAllAppointmentsByAppointmentIdentifier_AppointmentIdStartingWith_Should_Return_Correct_Appointments() {
        List<Appointment> result = appointmentRepository.findAllAppointmentsByAppointmentIdentifier_AppointmentIdStartingWith(savedAppointmentId);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(appointment -> appointment.getAppointmentIdentifier().getAppointmentId().equals(savedAppointmentId)));
    }
}
