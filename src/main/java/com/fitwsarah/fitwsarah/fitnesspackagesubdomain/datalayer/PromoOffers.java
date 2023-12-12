package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="promoOffers")
@Data
public class PromoOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private identifier


    @Embedded
    private PromoIdentifier promoIdentifier;

    private String title;
    private Boolean availability;
    private String description;
    private Double price;

    PromoOffers(){
        this.promoIdentifier = new PromoIdentifier();
    }

    public PromoOffers( String title, Boolean availability, String description, Double price) {
        this.promoIdentifier =  new PromoIdentifier();
        this.title = title;
        this.availability = availability;
        this.description = description;
        this.price = price;
    }
}
