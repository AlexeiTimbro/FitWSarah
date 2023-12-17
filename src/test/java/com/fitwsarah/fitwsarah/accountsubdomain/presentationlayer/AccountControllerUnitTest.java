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

        import java.util.Arrays;
        import java.util.List;

        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.is;
        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.anyString;
        import static org.mockito.Mockito.when;


class AccountControllerUnitTest {
    @Mock
    AccountService AccountService;

    @InjectMocks
    private AccountController accountController;

    AccountResponseModel account1 = new AccountResponseModel("uuid-appt1", "adms", "uuid-admin1", "uuid-service1", "Scheduled");
    AccountResponseModel account2 = new AccountResponseModel("uuid-appt1", "adms", "uuid-admin1", "uuid-service1", "Scheduled");
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

        when(AccountService.getAccountByAccountId(anyString())).thenReturn(account1);

        AccountResponseModel result = accountController.getAccountById(account1.getAccountId());


        assertThat(result, is(account1));
    }


    @Test
    void getAllAccounts_ShouldReturnAppointment() {

        // Arrange
        List<AccountResponseModel> AccountResponseModelList = Arrays.asList(
                account1,
                account2
        );

        when(AccountService.getAllAccounts()).thenReturn(AccountResponseModelList);

        List<AccountResponseModel> result = accountController.getAllAccounts();

        // Assert
        assertThat(result, is(AccountResponseModelList));
    }
}