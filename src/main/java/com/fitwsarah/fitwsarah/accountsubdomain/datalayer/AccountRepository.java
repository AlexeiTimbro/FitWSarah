package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Embeddable
public interface AccountRepository extends JpaRepository<Account, Integer> {
    //AccountId is now a string
    Account findAccountByAccountIdentifier_AccountId(String accountId);
    Account findAccountByUserId(String userId);
    List<Account> findAllAccountByAccountIdentifier_AccountIdStartingWith(String accountId);
    List<Account> findAllAccountByUsernameStartingWith(String username);
    List<Account> findAllAccountByEmailStartingWith(String email);
    List<Account> findAllAccountByCityStartingWith(String city);

    @Query("SELECT a FROM Account a WHERE " +
            "(:username IS NULL OR a.username LIKE CONCAT(:username, '%')) AND " +
            "(:email IS NULL OR a.email LIKE CONCAT(:email, '%')) AND " +
            "(:city IS NULL OR a.city LIKE CONCAT(:city, '%'))")
    List<Account> findAllAccountByUsernameEmailAndCityStartingWith(@Param("username") String username, @Param("email") String email, @Param("city") String city);

    @Query("SELECT a FROM Account a WHERE " +
            "(:username IS NULL OR a.username = :username) AND " +
            "(:city IS NULL OR a.city = :city)")
    List<Account> findAllAccountByUsernameAndCityStartingWith(@Param("username") String username, @Param("city") String city);

    @Query("SELECT a FROM Account a WHERE " +
            "(:username IS NULL OR a.username = :username) AND " +
            "(:email IS NULL OR a.email = :email)")
    List<Account> findAllAccountByUsernameAndEmailStartingWith(@Param("username") String username, @Param("email") String email);

    @Query("SELECT a FROM Account a WHERE " +
            "(:email IS NULL OR a.email = :email) AND " +
            "(:city IS NULL OR a.city = :city)")
    List<Account> findAllAccountByEmailAndCityStartingWith(@Param("email") String email, @Param("city") String city);
}
