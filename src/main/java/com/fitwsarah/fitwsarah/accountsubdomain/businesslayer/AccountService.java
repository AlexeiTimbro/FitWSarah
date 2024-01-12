package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;

import java.util.List;

public interface AccountService {

    List<AccountResponseModel> getAllAccounts();

    AccountResponseModel getAccountByAccountId(String accountId);

    AccountResponseModel getByUserId(String userId);

    AccountResponseModel addAccount(AccountRequestModel accountRequestModel);




    void removeAccount(String accountId);
}
