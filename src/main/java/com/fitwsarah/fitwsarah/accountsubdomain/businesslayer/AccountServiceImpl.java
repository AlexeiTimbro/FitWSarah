package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    private AccountResponseMapper accountResponseMapper;
    private AccountRequestMapper accountRequestMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountResponseMapper accountResponseMapper, AccountRequestMapper accountRequestMapper) {
        this.accountRepository = accountRepository;
        this.accountResponseMapper = accountResponseMapper;
        this.accountRequestMapper = accountRequestMapper;
    }
    @Override
    public List<AccountResponseModel> getAllAccounts() {
        return null;
    }

    @Override
    public AccountResponseModel getAccountByAccountId(String accountId) {
        return accountResponseMapper.entityToResponseModel(accountRepository.findByAccountIdentifier_AccountId(accountId));
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
