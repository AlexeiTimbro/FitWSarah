package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;

import java.util.List;

public interface AccountService {

    List<AccountResponseModel> getAllAccounts(String userId, String username, String email, String city);

    AccountResponseModel getAccountByAccountId(String accountId);

    AccountResponseModel getByUserId(String userId);

    AccountResponseModel addAccount(AccountRequestModel accountRequestModel);

    AccountResponseModel updateAccount(AccountRequestModel accountRequestModel, String userId);

    void deleteAccount(String accountId);
}
