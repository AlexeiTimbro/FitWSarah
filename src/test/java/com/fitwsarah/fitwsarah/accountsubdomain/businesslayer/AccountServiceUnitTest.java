package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
class AccountServiceUnitTest {
    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountResponseMapper accountResponseMapper;
    @Mock
    AccountRequestMapper accountRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAccountByAccountId_Should_Return_Correct_Account() {
        String accountId = "uuid-test1";
        Account account = new Account();
        account.getAccountIdentifier().setAccountId(accountId);
        AccountResponseModel responseModel = new AccountResponseModel("uuid-test1", "testUser", "uuid-admin1", "uuid-service1", "Active", new Date());

        when(accountRepository.findAccountByAccountIdentifier_AccountId(accountId)).thenReturn(account);
        when(accountResponseMapper.entityToResponseModel(account)).thenReturn(responseModel);

        AccountResponseModel result = accountService.getAccountByAccountId(accountId);

        assertEquals(accountId, result.getAccountId());
    }




    @Test
    void getAccountByAccountId_Should_Return_Null_When_Account_Not_Found() {
        String accountId = "uuid-test1";

        when(accountRepository.findAccountByAccountIdentifier_AccountId(accountId)).thenReturn(null);

        AccountResponseModel result = accountService.getAccountByAccountId(accountId);

        assertNull(result);
    }

    @Test
    void addAccount_Should_Return_Correct_Account() {
        AccountRequestModel requestModel = new AccountRequestModel("uuid-test1","testUser", "test@gmail.com", "Test City");
        Account entity = mock(Account.class);
        AccountResponseModel mockedResponse = new AccountResponseModel("1","uuid-test1","testUser","test@gmail.com","Test City", new Date());

        when(accountResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(accountRequestMapper.requestModelToEntity(requestModel)).thenReturn(entity);
        when(accountRepository.save(entity)).thenReturn(entity);

        AccountResponseModel result = accountService.addAccount(requestModel);

        assertNotNull(result);
        assertEquals("1", result.getAccountId());
        assertEquals(requestModel.getUsername(), result.getUsername());
    }

    @Test
    void getAllAccounts_Should_Return_All_Accounts_When_No_Filter_Is_Provided() {
        Account account = new Account("Test", "User", "test", "test");
        List<Account> accounts = Collections.singletonList(account);
        when(accountRepository.findAll()).thenReturn(accounts);

        AccountResponseModel responseModel = AccountResponseModel.builder()
                .accountId("1")
                .username("test")
                .email("test")
                .city("test")
                .build();

        List<AccountResponseModel> responseModels = Collections.singletonList(responseModel);
        when(accountResponseMapper.entityListToResponseModelList(accounts)).thenReturn(responseModels);

        List<AccountResponseModel> result = accountService.getAllAccounts(null, null, null, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAccountId(), result.get(0).getAccountId());
    }

    @Test
    void getAllAccounts_Should_Return_Filtered_Accounts_When_Filter_Is_Provided_AccountId() {
        String accountId = "uuid-test1";
        String userId = "uuid-admin1";
        String username = "user";
        String email = "email";
        String city = "Montreal";

        Account account = new Account();
        account.getAccountIdentifier().setAccountId(accountId);
        List<Account> accounts = Collections.singletonList(account);

        when(accountRepository.findAllAccountsByAccountIdentifier_AccountIdStartingWith(accountId)).thenReturn(accounts);
        when(accountRepository.findAllAccountByUsernameStartingWith(username)).thenReturn(accounts);
        when(accountRepository.findAllAccountByEmailStartingWith(email)).thenReturn(accounts);
        when(accountRepository.findAllAccountByCityStartingWith(city)).thenReturn(accounts);

        AccountResponseModel responseModel = new AccountResponseModel(accountId, userId, username, email, city, new Date());
        List<AccountResponseModel> responseModels = Collections.singletonList(responseModel);
        when(accountResponseMapper.entityListToResponseModelList(accounts)).thenReturn(responseModels);

        List<AccountResponseModel> result = accountService.getAllAccounts(accountId, username, email, city);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAccountId(), result.get(0).getAccountId());
        assertEquals(responseModels.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(responseModels.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(responseModels.get(0).getCity(), result.get(0).getCity());
    }

    @Test
    void getAllAccounts_Should_Return_Filtered_Accounts_When_Username_Is_Not_Null() {
        String userId = "uuid-admin1";
        String username = "user";
        String email = "email";
        String city = "Montreal";


        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setCity(city);
        List<Account> accounts = Collections.singletonList(account);

        when(accountRepository.findAllAccountByUsernameStartingWith(username)).thenReturn(accounts);
        when(accountRepository.findAllAccountByEmailStartingWith(email)).thenReturn(accounts);
        when(accountRepository.findAllAccountByCityStartingWith(city)).thenReturn(accounts);

        AccountResponseModel responseModel = new AccountResponseModel(null, userId, username, email, city, new Date());
        List<AccountResponseModel> responseModels = Collections.singletonList(responseModel);
        when(accountResponseMapper.entityListToResponseModelList(accounts)).thenReturn(responseModels);

        List<AccountResponseModel> result = accountService.getAllAccounts(null, username, null, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(responseModels.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(responseModels.get(0).getCity(), result.get(0).getCity());

        result = accountService.getAllAccounts(null, null, email, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(responseModels.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(responseModels.get(0).getCity(), result.get(0).getCity());

        result = accountService.getAllAccounts(null, null, null, city);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(responseModels.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(responseModels.get(0).getCity(), result.get(0).getCity());


    }

    @Test
    void getAllAccounts_Should_Return_Filtered_Accounts_When_email_Is_Not_Null() {
        String userId = "uuid-admin1";
        String username = "user";
        String email = "email";
        String city = "Montreal";


        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setCity(city);
        List<Account> accounts = Collections.singletonList(account);

        when(accountRepository.findAllAccountByUsernameStartingWith(username)).thenReturn(accounts);
        when(accountRepository.findAllAccountByEmailStartingWith(email)).thenReturn(accounts);
        when(accountRepository.findAllAccountByCityStartingWith(city)).thenReturn(accounts);

        AccountResponseModel responseModel = new AccountResponseModel(null, userId, username, email, city, new Date());
        List<AccountResponseModel> responseModels = Collections.singletonList(responseModel);
        when(accountResponseMapper.entityListToResponseModelList(accounts)).thenReturn(responseModels);

        List<AccountResponseModel> result = accountService.getAllAccounts(null, null, email, null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(responseModels.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(responseModels.get(0).getCity(), result.get(0).getCity());
    }


    @Test
    void updateAccountByUserId_Should_Update_Account_Correctly() {
        // Arrange
        String userId = "uuid-test1";
        AccountRequestModel requestModel = new AccountRequestModel(userId, "999", "00", "Brossard");
        Account mockedAccountEntity = mock(Account.class);
        AccountResponseModel expectedResponse = new AccountResponseModel("000", "999", "00", "Brossard", "China", new Date());
        when(accountRequestMapper.requestModelToEntity(requestModel)).thenReturn(mockedAccountEntity);
        when(accountRepository.findAccountByUserId(userId)).thenReturn(mockedAccountEntity); // Updated line
        when(accountRepository.save(mockedAccountEntity)).thenReturn(mockedAccountEntity);
        when(accountResponseMapper.entityToResponseModel(mockedAccountEntity)).thenReturn(expectedResponse);

        // Act
        AccountResponseModel result = accountService.updateAccount(requestModel, userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(accountRepository).save(mockedAccountEntity);
        verify(accountRequestMapper).requestModelToEntity(requestModel);
        verify(accountResponseMapper).entityToResponseModel(mockedAccountEntity);
    }


    @Test
    void updateAccountByUserId_WhenNoAccountFound_ShouldReturnNull() {
        // Arrange
        String userId = "uuid-test2";
        AccountRequestModel requestModel = new AccountRequestModel(userId, "999", "00", "Brossard");

        when(accountRepository.findAccountByUserId(userId)).thenReturn(null);

        // Act
        AccountResponseModel result = accountService.updateAccount(requestModel, userId);

        // Assert
        assertNull(result);
        verify(accountRepository, never()).save(any(Account.class));
        verify(accountRequestMapper, never()).requestModelToEntity(any(AccountRequestModel.class));
        verify(accountResponseMapper, never()).entityToResponseModel(any(Account.class));
    }


}