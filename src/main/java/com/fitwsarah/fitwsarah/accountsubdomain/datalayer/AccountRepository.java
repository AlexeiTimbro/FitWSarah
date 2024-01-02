package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface AccountRepository extends JpaRepository<Account, Integer> {
    //AccountId is now a string
  Account findAccountByAccountIdentifier_AccountId(String accountId);
    Account findAccountByUserId(String userId);
}
