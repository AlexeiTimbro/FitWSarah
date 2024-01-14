package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AccountIdentifier {
    private String accountId;

    public AccountIdentifier() {
        this.accountId = UUID.randomUUID().toString();

    }
    public AccountIdentifier(String accountId){
        this.accountId = accountId;
    }



    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
