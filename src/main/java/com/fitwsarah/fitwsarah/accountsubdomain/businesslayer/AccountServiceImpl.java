package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{


    @Override
    public List<AccountResponseModel> getAllAccounts() {
        return null;
    }

    @Override
    public AccountResponseModel getAccountByAccountId(String accountId) {
        return null;
    }

    @Override
    public AccountResponseModel addAccount(AccountRequestModel accountRequestModel, String accountId) {
        return null;
    }

    @Override
    public AccountResponseModel updateAccount(AccountRequestModel accountRequestModel, String accountId) {
        return null;
    }

    @Override
    public void removeAccount(String accountId) {

    }

}
