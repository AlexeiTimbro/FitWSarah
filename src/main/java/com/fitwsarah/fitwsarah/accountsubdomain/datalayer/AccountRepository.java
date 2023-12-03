package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountByAccountIdentifier_AccountId(String accountId);
}
