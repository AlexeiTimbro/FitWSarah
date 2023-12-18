package com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class AppointmentRepositoryTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private String savedAppointmentId;

    private String accountId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountId = "uuid-account1";
        Appointment appointment = new Appointment();
        AppointmentIdentifier identifier = new AppointmentIdentifier();
        appointment.setAppointmentIdentifier(identifier);
    }

    @AfterEach
    public void tearDown() {
        accountId = null;
    }

    @Test
    public void whenFindByCustomerId_thenReturnAppointment() {
        // Arrange
        String mockAppointmentId = "6e83092a-1202-41f1-9d98-1394b52f1d3c";
        Appointment mockAppointment = new Appointment();
        AppointmentIdentifier identifier = new AppointmentIdentifier();
        identifier.setAppointmentId(mockAppointmentId);
        mockAppointment.setAppointmentIdentifier(identifier);

        when(appointmentRepository.findAppointmentsByAppointmentIdentifier_AppointmentId(mockAppointmentId)).thenReturn(mockAppointment);

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
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentRepository.findAppointmentByAccountIdentifier_AccountId(accountId)).thenReturn(appointments);

        List<Appointment> result = appointmentRepository.findAppointmentByAccountIdentifier_AccountId(accountId);

        assertEquals(appointments, result);
    }

    @Test
    public void whenFindAppointmentByNonExistentAccountIdentifier_AccountIdReturnsEmptyList() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());

        when(appointmentRepository.findAppointmentByAccountIdentifier_AccountId(accountId)).thenReturn(appointments);

        List<Appointment> result = appointmentRepository.findAppointmentByAccountIdentifier_AccountId(accountId);

        assertEquals(appointments, result);
    }
}
