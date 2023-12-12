package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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
        return accountResponseMapper.entityListToResponseModelList(accountRepository.findAll());
    }

    @Override
    public AccountResponseModel getAccountByAccountId(String accountId) {
        return accountResponseMapper.entityToResponseModel(accountRepository.findByAccountIdentifier_AccountId(accountId));
    }

    @Override
    public AccountResponseModel addAccount(AccountRequestModel accountRequestModel) {
        Account account = accountRequestMapper.requestModelToEntity(accountRequestModel);
        Account saved = accountRepository.save(account);
        return accountResponseMapper.entityToResponseModel(saved);
    }

    @Override
    public AccountResponseModel updateAccount(AccountRequestModel accountRequestModel, String accountId) {
        return null;
    }

    @Override
    public void removeAccount(String accountId) {

    }



}
