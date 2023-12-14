package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:mysql://localhost:8080/accounts"})
@AutoConfigureWebTestClient
class AccountControllerIntegrationTest {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    AccountRepository accountRepository;

    @MockBean
    AccountService accountService;

    private List<AccountResponseModel> accountList;

    AccountResponseModel accountResponseModel1 = new AccountResponseModel("1","john","johnnn","john@gmail.com",  "John City");
    AccountResponseModel accountResponseModel2 = new AccountResponseModel("2","john2","johnnn2","john2@gmail.com",  "John2 City");

    @BeforeEach
    void setUp() {
        accountList = Arrays.asList(
                accountResponseModel1,
                accountResponseModel2
        );
        given(accountService.getAllAccounts()).willReturn(accountList);
    }
    @Test
    public void addAccount_shouldSucceed() {
        AccountRequestModel requestModel = new AccountRequestModel("smith", "johnjohn", "john@gmail.com", "John Ville");

        webTestClient.post()
                .uri("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponseModel.class)
                .value(responseModel -> {
                    assertNotNull(responseModel);
                    assertEquals(requestModel.getUsername(), responseModel.getUsername());
                });
    }

}