package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceUnitTest {
    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    AccountResponseMapper accountResponseMapper;

    @MockBean
    AccountRequestMapper accountRequestMapper;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void addAccount_ShouldSucceed() {

        AccountRequestModel requestModel = new AccountRequestModel("uuid122","smith", "john@gmail.com", "John Ville");

        Account entity = mock(Account.class);

        AccountResponseModel mockedResponse = new AccountResponseModel("uuid-122","smith","john@gmail.com","John Ville");
        when(accountResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(accountRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(accountRepository.save(entity)).thenReturn(entity);


        AccountResponseModel result = accountService.addAccount(requestModel);

        assertNotNull(result);
        assertNotNull(result.getAccount_Id());
        assertNotNull(result.getUsername());

    }

}