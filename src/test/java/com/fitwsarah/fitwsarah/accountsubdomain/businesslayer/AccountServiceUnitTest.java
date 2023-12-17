package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceUnitTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountResponseMapper accountResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAccountByAccountId_Should_Succeed() {

        String accountId = "uuid-appt1";

        Account account = new Account();
        account.getAccountIdentifier().setAccountId(accountId);

        AccountResponseModel responseModel = new AccountResponseModel("uuid-appt1", "adms", "uuid-admin1", "uuid-service1", "Scheduled");

        // Fix the method name in the following line (from `appointment` to `account`)
        when(accountRepository.findByAccountIdentifier_AccountId(accountId)).thenReturn(account);

        // Fix the method name in the following line (from `appointment` to `account`)
        when(accountResponseMapper.entityToResponseModel(account)).thenReturn(responseModel);

        AccountResponseModel result = accountService.getAccountByAccountId(accountId);

        assertEquals(accountId, result.getAccountId());
    }
}
