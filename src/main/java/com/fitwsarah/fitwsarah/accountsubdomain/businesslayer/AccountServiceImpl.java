package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageRequestMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<AccountResponseModel> getAllAccounts(String accountId, String username, String email, String city) {
        List<Account> filteredAccounts;

        if (accountId != null) {
            filteredAccounts = accountRepository.findAllAccountByAccountIdentifier_AccountIdStartingWith(accountId);
        } else {
            if (username != null && email != null && city != null) {
                filteredAccounts = accountRepository.findAllAccountByUsernameEmailAndCityStartingWith(username, email, city);
            } else if (username != null && email != null) {
                filteredAccounts = accountRepository.findAllAccountByUsernameAndEmailStartingWith(username, email);
            } else if (username != null && city != null) {
                filteredAccounts = accountRepository.findAllAccountByUsernameAndCityStartingWith(username, city);
            } else if (email != null && city != null) {
                filteredAccounts = accountRepository.findAllAccountByEmailAndCityStartingWith(email, city);
            } else if (username != null) {
                filteredAccounts = accountRepository.findAllAccountByUsernameStartingWith(username);
            } else if (email != null) {
                filteredAccounts = accountRepository.findAllAccountByEmailStartingWith(email);
            } else if (city != null) {
                filteredAccounts = accountRepository.findAllAccountByCityStartingWith(city);
            } else {
                filteredAccounts = accountRepository.findAll();
            }
        }

        return accountResponseMapper.entityListToResponseModelList(filteredAccounts);
    }


    @Override
    public AccountResponseModel getAccountByAccountId(String accountId) {
        return accountResponseMapper.entityToResponseModel(accountRepository.findAccountByAccountIdentifier_AccountId(accountId));
    }

    @Override
    public AccountResponseModel getByUserId(String userId) {
        return accountResponseMapper.entityToResponseModel(accountRepository.findAccountByUserId(userId));
    }
    @Override
    public AccountResponseModel addAccount(AccountRequestModel accountRequestModel) {
        Account account = accountRequestMapper.requestModelToEntity(accountRequestModel);
        Account saved = accountRepository.save(account);
        return accountResponseMapper.entityToResponseModel(saved);
    }


    @Override
    public void removeAccount(String accountId) {

    }



}
