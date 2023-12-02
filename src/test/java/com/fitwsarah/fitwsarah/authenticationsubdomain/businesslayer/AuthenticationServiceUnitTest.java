package com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.authenticationsubdomain.datalayer.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthenticationServiceUnitTest {
    @Autowired
    AuthenticationService authenticationService;
    @MockBean
    AuthenticationRepository authenticationRepository;
}