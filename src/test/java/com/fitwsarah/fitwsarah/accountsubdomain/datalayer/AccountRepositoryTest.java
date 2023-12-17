package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
}