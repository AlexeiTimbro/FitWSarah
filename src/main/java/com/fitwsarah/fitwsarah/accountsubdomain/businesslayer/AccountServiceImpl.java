package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{


    @Override
    public Flux<AccountResponseModel> getAllAccounts() {
        return null;
    }

    @Override
    public Mono<AccountResponseModel> getAccountByAccountId(String accountId) {
        return null;
    }

    @Override
    public Mono<AccountResponseModel> addAccount(AccountRequestModel accountRequestModel, String accountId) {
        return null;
    }

    @Override
    public Mono<AccountResponseModel> updateAccount(AccountRequestModel accountRequestModel, String accountId) {
        return null;
    }

    @Override
    public Mono<Void> removeAccount(String accountId) {
        return null;
    }



}
