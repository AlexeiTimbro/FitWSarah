package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts")
public class AccountController {
    private AccountService accountService;


    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountResponseModel> getAllAccounts() {
        return null;
    }

    @GetMapping("/{accountId}")
    public Mono<ResponseEntity<AccountResponseModel>> getAccountById(@PathVariable String accountId) {
        return null;
    }

    @PostMapping()
    public Mono<AccountResponseModel> addAccount(@RequestBody Mono<AccountRequestModel> accountRequestModel) {
        return null;
    }

    @PutMapping("/{accountId}")
    public Mono<AccountResponseModel> updateAccount(@RequestBody Mono<AccountRequestModel> accountRequestModel, @PathVariable String accountId) {
        return null;
    }

    @DeleteMapping("/{accountId}")
    public Mono<ResponseEntity<Void>> deleteAccountById(@PathVariable String accountId) {
    return null;
    }

    @DeleteMapping()
    public Mono<ResponseEntity<Void>> deleteAllAccounts() {
    return null;
    }
}
