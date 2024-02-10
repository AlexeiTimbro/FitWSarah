package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private AccountIdentifier accountIdentifier;
    private String userId;
    private String username;
    private String email;
    private String city;
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    public Account() {
        this.accountIdentifier = new AccountIdentifier();
    }


    public Account(String userId, String username, String email, String city) {
        this.accountIdentifier = new AccountIdentifier();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.city = city;
    }
}
