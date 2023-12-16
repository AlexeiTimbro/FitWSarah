package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String account_Id;
    private String username;
    private String email;
    private String city;



    public Account() {}

    public Account(String account_Id, String username, String email, String city) {
        this.account_Id = account_Id;
        this.username = username;
        this.email = email;
        this.city = city;
    }
}
