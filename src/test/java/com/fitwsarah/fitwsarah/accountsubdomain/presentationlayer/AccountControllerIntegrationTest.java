package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
        import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountIdentifier;
        import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.http.MediaType;
        import org.springframework.test.web.reactive.server.WebTestClient;
        import org.springframework.test.web.servlet.MockMvc;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.test.web.servlet.MockMvc;

        import java.util.List;

        import static org.hamcrest.Matchers.hasSize;


        import static org.mockito.Mockito.mock;
        import static org.mockito.Mockito.verify;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

        import static org.hamcrest.Matchers.hasSize;

        import org.springframework.http.*;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import org.springframework.web.client.RestTemplate;
        import com.fasterxml.jackson.databind.JsonNode;
        import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    private Account testAccount;
    private String testAccountId;

    private String testToken = "Bearer ";

    @BeforeEach
    void setUp() throws Exception {
        AccountIdentifier identifier = new AccountIdentifier();
        testAccount = new Account("John", "Doe", "d", "d");
        testAccount.setAccountIdentifier(identifier);
        Account savedAccount = accountRepository.save(testAccount);
        testAccountId = savedAccount.getAccountIdentifier().getAccountId();

        testToken += obtainAuthToken();
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void getAllAccounts_ShouldReturnAccounts_WhenCalledWithValidToken() throws Exception {
        mockMvc.perform(get("/api/v1/accounts")
                        .header("Authorization", testToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getAccountById_ShouldReturnAccount_WhenCalledWithValidToken() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/{accountId}", testAccountId)
                        .header("Authorization", testToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void addAccount_shouldSucceed() throws Exception {
        AccountRequestModel requestModel = new AccountRequestModel("3","smith", "john@gmail.com", "John Ville");

        mockMvc.perform(post("/api/v1/accounts")
                        .header("Authorization", testToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isCreated());

    }

    @Test
    public void getAllAccounts_ShouldReturnOk() throws Exception {
        AccountRequestModel requestModel = new AccountRequestModel("3","smith", "john@gmail.com", "John Ville");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts")
                .header("Authorization", testToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestModel)))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public String obtainAuthToken() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{"
                + "\"client_id\": \"3UMAwvOXQsi1UiaRq9eM3gQubsMOcOYt\","
                + "\"client_secret\": \"ASrwPK-aY36ZxvVI35m70JbztjZLxfn8LRnWd3z5LuATj8HcnWnze_yaZ-sSW-x9\","
                + "\"audience\": \"https://dev-twa7h1nv0usycyum.us.auth0.com/api/v2/\","
                + "\"grant_type\": \"client_credentials\""
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://dev-twa7h1nv0usycyum.us.auth0.com/oauth/token", entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        return rootNode.path("access_token").asText();
    }

}

