package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Embeddable
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByAccountIdentifier_AccountId(String accountId);
    Account findAccountByUserId(String userId);
    List<Account> findAllAccountsByAccountIdentifier_AccountIdStartingWith(String accountId);


    List<Account> findAllAccountByUsernameStartingWith(String username);
    List<Account> findAllAccountByEmailStartingWith(String email);
    List<Account> findAllAccountByCityStartingWith(String city);
}
