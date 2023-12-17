package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentIdentifier;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.*;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;



    private String savedAccountId;

    @BeforeEach
    public void setUp() {
        Account account = new Account();
        AccountIdentifier identifier = new AccountIdentifier();
        identifier.setAccountId(savedAccountId);

        // Save the appointment and get the saved ID
        Account savedAccount = accountRepository.save(account);
        savedAccountId = savedAccount.getAccountIdentifier().getAccountId();
    }

    @AfterEach
    public void tearDown() {
        // Delete all appointments after each test
        accountRepository.deleteAll();
    }

    @Test
    public void whenFindByCustomerId_thenReturnAppointment() {
        // Arrange
        assertNotNull(savedAccountId);

        // Act
        Account found = accountRepository.findByAccountIdentifier_AccountId(savedAccountId);

        // Assert
        assertNotNull(found);
        assertEquals(savedAccountId, found.getAccountIdentifier().getAccountId());
    }

    @Test
    public void whenFindByNonExistentCustomerId_thenReturnNull() {
        // Arrange
        String nonExistentAcountId = "nonExistentId";

        // Act
        Account found = accountRepository.findByAccountIdentifier_AccountId(nonExistentAcountId);

        // Assert
        assertNull(found);
    }
}
