package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class AccountControllerUnitTest {
    @Mock
    AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    AccountResponseModel account1 = new AccountResponseModel("uuid-1", "appt1", "adms", "uuid-admin1", "Scheduled", new Date());
    AccountResponseModel account2 = new AccountResponseModel("uuid-2", "appt1", "adms", "uuid-admin1", "Scheduled", new Date());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAccountById_ShouldReturnAppointment() {

        List<AccountResponseModel> AccountResponseModelList = Arrays.asList(
                account1,
                account2
        );
    }

    @Test
    public void AddAccount_withInvalidKey_ShouldFail() {
        AccountRequestModel requestModel = new AccountRequestModel("3", "smith", "john@gmail.com", "John Ville");
        AccountResponseModel addedAccount = new AccountResponseModel("3", "3", "smith", "john@gmail.com", "John Ville", new Date());

        when(accountService.addAccount(requestModel)).thenReturn(addedAccount);

        ResponseEntity<AccountResponseModel> result = accountController.addAccount(requestModel);

        assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(result.getBody(), is(addedAccount));

    }

    @Test
    void getAllAccounts_ShouldReturnAppointment() {

        List<AccountResponseModel> AccountResponseModelList = Arrays.asList(
                account1,
                account2
        );

        when(accountService.getAllAccounts(null, null, null, null)).thenReturn(AccountResponseModelList);

        List<AccountResponseModel> result = accountController.getAllAccounts(null, null, null, null);

        assertThat(result, is(AccountResponseModelList));
    }

    @Test
    void updateAccountByUserId_ShouldUpdateAccount() {
        // Arrange
        String userId = "uuid-1";
        AccountRequestModel updateRequestModel = new AccountRequestModel("JohnDoe", "johndoe@example.com", "123 Main St", "tonrnot");
        AccountResponseModel updatedAccount = new AccountResponseModel("uuid-1", "JohnDoe", "johndoe@example.com", "123 Main St" , "tonrnot", new Date());

        when(accountService.updateAccount(updateRequestModel, userId)).thenReturn(updatedAccount);

        // Act
        ResponseEntity<AccountResponseModel> result = accountController.updateAccountByUserId(updateRequestModel, userId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(updatedAccount, result.getBody());
    }


}