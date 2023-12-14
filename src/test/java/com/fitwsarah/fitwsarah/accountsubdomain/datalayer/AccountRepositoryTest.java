package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;



    @Test
    public void ShouldSaveSingleAccount(){
        //arrange
        Account newAccount = buildAccount("user1","pass","email@gmail.com","New York");
        Account setup = accountRepository.save(newAccount);
        //Act and Assert
        assertNotNull(setup);
        assertEquals("user1", setup.getUsername());
        assertEquals("pass", setup.getPassword());
        assertEquals("email@gmail.com", setup.getEmail());
        assertEquals("New York", setup.getCity());
    }


    private Account buildAccount(String username, String password, String email, String city) {
        return new Account(username, password, email, city);
    }
}