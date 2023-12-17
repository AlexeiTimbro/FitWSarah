package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AccountIdentifier {
    private String accountId;


    public String getAccountId(){
        return accountId;
    }
}
