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

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
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
        Set<Account> filteredAccounts = new HashSet<>();

        // Existing logic to filter accounts based on provided parameters
        if (accountId != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountsByAccountIdentifier_AccountIdStartingWith(accountId));
        } else if (username != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountByUsernameStartingWith(username));
        } else if (email != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountByEmailStartingWith(email));
        } else if (city != null) {
            filteredAccounts.addAll(accountRepository.findAllAccountByCityStartingWith(city));
        } else {
            filteredAccounts.addAll(accountRepository.findAll());
        }

        List<String> auth0Usernames = getAuth0Usernames();

        for (String auth0Username : auth0Usernames) {
            boolean accountExists = filteredAccounts.stream()
                    .anyMatch(account -> account.getUsername().equals(auth0Username));
            if (!accountExists) {
                Account newAccount = new Account();
                newAccount.setUsername(auth0Username);
                filteredAccounts.add(newAccount);
            }
        }

        return accountResponseMapper.entityListToResponseModelList(
                filteredAccounts.stream()
                        .sorted(Comparator.comparing(account -> account.getAccountIdentifier().getAccountId()))
                        .toList()
        );
    }

    private List<String> getAuth0Usernames() {
        // Placeholder method - implement the actual logic to fetch usernames from Auth0
        return new ArrayList<>(); // Replace with actual fetched usernames
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
    public AccountResponseModel updateAccount(AccountRequestModel accountRequestModel, String userId) {
        Account existingAccount = accountRepository.findAccountByUserId((userId));
        if(existingAccount == null){
            return  null;
        }
        Account account = accountRequestMapper.requestModelToEntity(accountRequestModel);
        account.setId(existingAccount.getId());
        account.setAccountIdentifier(existingAccount.getAccountIdentifier());
        account.setUserId(existingAccount.getUserId());
        return accountResponseMapper.entityToResponseModel(accountRepository.save(account));
    }


    @Override
    public void removeAccount(String accountId) {

    }
}
