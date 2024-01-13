package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentIdentifier;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;


    private String savedAccountId;
    Account savedAccount;

    @BeforeEach
    public void setUp() {
        Account account = new Account();
        AccountIdentifier identifier = new AccountIdentifier();
        identifier.setAccountId(savedAccountId);
        account.setUsername("user1");
        account.setEmail("email");
        account.setCity("New York");

        // Save the appointment and get the saved ID
        savedAccount = accountRepository.save(account);
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
        Account found = accountRepository.findAccountByAccountIdentifier_AccountId(savedAccountId);

        // Assert
        assertNotNull(found);
        assertEquals(savedAccountId, found.getAccountIdentifier().getAccountId());
    }

    @Test
    public void ShouldSaveSingleAccount() {
        //arrange
        Account newAccount = buildAccount("1", "user1", "email@gmail.com", "New York");
        Account setup = accountRepository.save(newAccount);
        //Act and Assert
        assertNotNull(setup);
        assertEquals("user1", setup.getUsername());
        assertEquals("email@gmail.com", setup.getEmail());
        assertEquals("New York", setup.getCity());
    }

    @Test
    public void whenFindByNonExistentCustomerId_thenReturnNull() {
        // Arrange
        String nonExistentAcountId = "nonExistentId";

        // Act
        Account found = accountRepository.findAccountByAccountIdentifier_AccountId(nonExistentAcountId);

        // Assert
        assertNull(found);
    }
    private Account buildAccount(String accountId, String username, String email, String city) {
        return new Account(accountId, username, email, city);
    }

    @Test
    void findAllAccountsByAccountIdentifier_AccountIdStartingWith_Should_Return_Correct_Accounts() {
        List<Account> result = accountRepository.findAllAccountsByAccountIdentifier_AccountIdStartingWith(savedAccountId);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(account -> account.getAccountIdentifier().getAccountId().equals(savedAccountId)));
    }

    @Test
    void findAllAccountByUsernameStartingWith_Should_Return_Correct_Accounts() {
        List<Account> result = accountRepository.findAllAccountByUsernameStartingWith(savedAccount.getUsername());

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(account -> account.getUsername().equals(savedAccount.getUsername())));
    }

    @Test
    void findAllAccountByEmailStartingWith_Should_Return_Correct_Accounts() {
        List<Account> result = accountRepository.findAllAccountByEmailStartingWith(savedAccount.getEmail());

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(account -> account.getEmail().equals(savedAccount.getEmail())));
    }

    @Test
    void findAllAccountByCityStartingWith_Should_Return_Correct_Accounts() {
        List<Account> result = accountRepository.findAllAccountByCityStartingWith(savedAccount.getCity());

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(account -> account.getCity().equals(savedAccount.getCity())));
    }
}

