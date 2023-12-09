package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {

    Flux<AccountResponseModel> getAllAccounts();

    Mono<AccountResponseModel> getAccountByAccountId(String accountId);

    Mono<AccountResponseModel> addAccount(AccountRequestModel accountRequestModel, String accountId);

    Mono<AccountResponseModel> updateAccount(AccountRequestModel accountRequestModel, String accountId);

    Mono<Void> removeAccount(String accountId);
}
