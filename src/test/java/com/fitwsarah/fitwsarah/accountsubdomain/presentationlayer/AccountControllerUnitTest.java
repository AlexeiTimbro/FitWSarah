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
        import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebFluxTest(controllers = AccountController.class)
class AccountControllerUnitTest {

    @Autowired
    WebTestClient webTestClient;

    //THE ID BELOW WORKS ON EMILE'S COMPUTER WILL HAVE TO FIX W/E IS WRONG WITH MINE
    private String testToken = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjlBNEZQeXE4LTh1OVZqR1IycmJQTiJ9..h---_3EE-0gQ6oKpSQm1-85_icKtLH40u8S6GR9stSCBoBwI7tuaHcX0TJ47QKvRGl4zqY9LxvCrEJ392MFAVn0te-BOmUQgEqzG9WiYz-87psSx8Q1GDPR8kDnmBx6fPTwexxTWPUPgc3dyeR2j5BGfjl4qJp0hk0ZH1h61FLk75Cx-ojFis2UVWbswmPqIj_o0bstxCwle4UDoyr5PIb88vE0_BTmx1WtW-kjZguzcWhXX7U1aB9fD18Qw7g";
    @MockBean
    AccountService accountService;

    @InjectMocks
    private AccountController accountController;



    AccountResponseModel account1 = account1 = new AccountResponseModel("uuid-1","appt1", "adms", "uuid-admin1",  "Scheduled");
    AccountResponseModel account2 = account2 = new AccountResponseModel("uuid-2","appt1", "adms", "uuid-admin1", "Scheduled");

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
        AccountRequestModel requestModel = new AccountRequestModel("uuid-1","smith", "john@gmail.com", "John Ville");
        AccountResponseModel response = new AccountResponseModel("uuid-2","uuid-1", "smith", "john@gmail.com","John Ville");

        // Mock the behavior of the AccountService
        when(accountService.addAccount(requestModel)).thenReturn(response);

        webTestClient.post()
                .uri("/api/v1/accounts")
                .header("Authorization", testToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestModel)
                .exchange()
                .expectStatus().isUnauthorized();

                /*
                .expectBody(AccountResponseModel.class)
                .value(responseModel -> {
                    assertNotNull(responseModel);
                    assertEquals(requestModel.getUsername(), responseModel.getUsername());
                })
                .consumeWith(responseResult -> {
                });
                 */

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