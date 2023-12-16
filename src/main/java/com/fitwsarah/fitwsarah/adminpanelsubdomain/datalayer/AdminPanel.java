package com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="adminAccounts")
@Data
public class AdminPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Embedded
    private AdminPanelIdentifier adminPanelIdentifier;

    private String username;
    private String email;
    private String role;
    private String city;



    AdminPanel(){
        this.adminPanelIdentifier = new AdminPanelIdentifier();
    }

    public AdminPanel(String username, String email, String role, String city) {
        this.adminPanelIdentifier =  new AdminPanelIdentifier();
        this.username = username;
        this.email = email;
        this.role = role;
        this.city = city;
    }
}
