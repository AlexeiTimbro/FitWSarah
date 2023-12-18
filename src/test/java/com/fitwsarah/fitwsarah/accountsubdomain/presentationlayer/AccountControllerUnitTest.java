package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;
import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.JsonPathAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


class AccountControllerUnitTest {

    @Autowired
    WebTestClient webTestClient;

    //THE ID BELOW WORKS ON EMILE'S COMPUTER WILL HAVE TO FIX W/E IS WRONG WITH MINE
    private String testToken = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjlBNEZQeXE4LTh1OVZqR1IycmJQTiJ9..h---_3EE-0gQ6oKpSQm1-85_icKtLH40u8S6GR9stSCBoBwI7tuaHcX0TJ47QKvRGl4zqY9LxvCrEJ392MFAVn0te-BOmUQgEqzG9WiYz-87psSx8Q1GDPR8kDnmBx6fPTwexxTWPUPgc3dyeR2j5BGfjl4qJp0hk0ZH1h61FLk75Cx-ojFis2UVWbswmPqIj_o0bstxCwle4UDoyr5PIb88vE0_BTmx1WtW-kjZguzcWhXX7U1aB9fD18Qw7g";
    @Mock
    AccountService accountService;

    @InjectMocks
    private AccountController accountController;



    AccountResponseModel account1 = new AccountResponseModel("uuid-1","appt1", "adms", "uuid-admin1",  "Scheduled");
    AccountResponseModel account2 = new AccountResponseModel("uuid-2","appt1", "adms", "uuid-admin1", "Scheduled");

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
    public void AddAccount_withInvalidKey_ShouldFail(){
        // Arrange
        AccountRequestModel requestModel = new AccountRequestModel("3", "smith", "john@gmail.com", "John Ville");
        AccountResponseModel addedAccount = new AccountResponseModel("3","3", "smith", "john@gmail.com", "John Ville");

        when(accountService.addAccount(requestModel)).thenReturn(addedAccount);

        // Act
        ResponseEntity<AccountResponseModel> result = accountController.addAccount(requestModel);

        // Assert
        assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(result.getBody(), is(addedAccount));

    }

    @Test
    void getAllAccounts_ShouldReturnAppointment() {

        // Arrange
        List<AccountResponseModel> AccountResponseModelList = Arrays.asList(
                account1,
                account2
        );

        when(accountService.getAllAccounts()).thenReturn(AccountResponseModelList);

        List<AccountResponseModel> result = accountController.getAllAccounts();

        // Assert
        assertThat(result, is(AccountResponseModelList));
    }
}