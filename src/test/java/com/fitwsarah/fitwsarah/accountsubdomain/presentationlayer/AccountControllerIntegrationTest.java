package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private AccountResponseModel account1 = new AccountResponseModel("uuid-appt1", "adms", "uuid-admin1", "uuid-service1", "Scheduled");

    private List<AccountResponseModel> accountResponseModelList;


    @Autowired
    private AccountRepository accountRepository;

    private Account testAccount;
    private String testAccountId;




    @BeforeEach
    void setUp() {
        accountResponseModelList = Arrays.asList(account1);

        given(accountService.getAllAccounts()).willReturn(accountResponseModelList);
        given(accountService.getAccountByAccountId(account1.getAccountId()))
                .willReturn(account1);
    }

    @Test
    void getAccountbyAccountId_Should_Return_Unauthorized() throws Exception {
        String actualAccountId = "invalid_id";
        String token = "invalid_token";
        mockMvc.perform(get("/api/v1/accounts/{accountId}", actualAccountId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

}
