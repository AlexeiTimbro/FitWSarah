package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AccountController.class)
class AccountControllerUnitTest {
    @Autowired
    WebTestClient webTestClient;
    @MockBean
    AccountService accountService;
    @InjectMocks
    private AccountController accountController;
    @Test
    public void AddAccount_ShouldSucceed(){
        AccountRequestModel requestModel = new AccountRequestModel("smith", "johnjohn", "john@gmail.com", "John Ville");
        AccountResponseModel response = new AccountResponseModel("uuid-1", "smith", "johnjohn", "john@gmail.com","John Ville");

            // Mock the behavior of the AccountService
            when(accountService.addAccount(requestModel)).thenReturn(response);

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
                    });;
        }
    }