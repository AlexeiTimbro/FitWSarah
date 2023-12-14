package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fitnessServices")
@Data
public class FitnessPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private id

    @Embedded
    private FitnessPackageIdentifier fitnessPackageIdentifier; //public id
    @Embedded
    private PromoIdentifier promoIdentifier;

    private String title;
    private String duration;
    private String description;
    private String otherInformation;
    private Double price;


    public FitnessPackage() {
        this.fitnessPackageIdentifier = new FitnessPackageIdentifier();
    }

    public FitnessPackage( PromoIdentifier promoIdentifier, String title, String duration, String description,String otherInformation, Double price) {
        this.fitnessPackageIdentifier =  new FitnessPackageIdentifier();
        this.promoIdentifier = promoIdentifier;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.otherInformation = otherInformation;
        this.price = price;
    }
}
