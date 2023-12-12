package com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AdminPanelIdentifier {
    private String adminId;
<<<<<<< HEAD
    public AdminPanelIdentifier(){
=======
    AdminPanelIdentifier(){
>>>>>>> parent of c566783 (Testing is now fully completed)
        this.adminId = UUID.randomUUID().toString();
    }

    public String getAdminId() {
        return adminId;
    }
}
