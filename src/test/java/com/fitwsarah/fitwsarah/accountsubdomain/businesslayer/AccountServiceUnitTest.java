package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountResponseMapper accountResponseMapper;
    @Mock
    AccountRequestMapper accountRequestMapper;


    @Test
    public void getAccountByAccountId_Should_Succeed() {

        String accountId = "uuid-appt1";


        Account account = new Account();
        account.getAccountIdentifier().setAccountId(accountId);

        AccountResponseModel responseModel = new AccountResponseModel("uuid-appt1", "adms", "uuid-admin1", "uuid-service1", "Scheduled");

        // Fix the method name in the following line (from `appointment` to `account`)

        when(accountRepository.findAccountByAccountIdentifier_AccountId(accountId)).thenReturn(account);

        // Fix the method name in the following line (from appointment to account)
        when(accountResponseMapper.entityToResponseModel(account)).thenReturn(responseModel);
        AccountResponseModel result = accountService.getAccountByAccountId(accountId);
        assertEquals(accountId, result.getAccountId());

    }
    @Test
    void addAccount_ShouldSucceed() {

        AccountRequestModel requestModel = new AccountRequestModel("uuid122","smith", "john@gmail.com", "John Ville");

        Account entity = mock(Account.class);
        AccountResponseModel mockedResponse = new AccountResponseModel("1","uuid-122","smith","john@gmail.com","John Ville");
        when(accountResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(accountRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(accountRepository.save(entity)).thenReturn(entity);


        AccountResponseModel result = accountService.addAccount(requestModel);

        assertNotNull(result);
        assertNotNull(result.getAccountId());
        assertNotNull(result.getUsername());

    }

    @Test
    void getAllAccounts_Should_Succeed() {
        // Act
        Account account = new Account("Sarah", "Fitzpatrick", "s", "s");

        List<Account> accounts = Collections.singletonList(account);
        when(accountRepository.findAll()).thenReturn(accounts);

        AccountResponseModel responseModel = AccountResponseModel.builder()
                .accountId("1")
                .username("s")
                .email("s")
                .city("s")
                .build();

        //Arrange
        List<AccountResponseModel> responseModels = Collections.singletonList(responseModel);

        when(accountResponseMapper.entityListToResponseModelList(accounts)).thenReturn(responseModels);

        List<AccountResponseModel> result = accountService.getAllAccounts();

        // Assert

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAccountId(), result.get(0).getAccountId());

        verify(accountRepository, times(1)).findAll();
        verify(accountResponseMapper, times(1)).entityListToResponseModelList(accounts);
    }

}