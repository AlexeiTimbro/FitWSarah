package com.fitwsarah.fitwsarah.authenticationsubdomain.datalayer;

import com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AuthenticationRepositoryTest {
    @Autowired
    AuthenticationRepository  authenticationRepository;

}