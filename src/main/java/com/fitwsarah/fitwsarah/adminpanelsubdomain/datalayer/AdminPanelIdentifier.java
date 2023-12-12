package com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AdminPanelIdentifier {

    private String adminId;

    AdminPanelIdentifier(){
        this.adminId = UUID.randomUUID().toString();
    }

    public String getAdminId() {
        return adminId;
    }
}

