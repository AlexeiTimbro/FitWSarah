package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
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

    @Enumerated(EnumType.STRING)
    private Status status;
    private String title_EN;
    private String title_FR;
    private String duration;
    private String description_EN;
    private String description_FR;
    private String otherInformation_EN;
    private String otherInformation_FR;
    private Double price;


    public FitnessPackage() {
        this.fitnessPackageIdentifier = new FitnessPackageIdentifier();
    }

    public FitnessPackage( PromoIdentifier promoIdentifier, Status status, String title_EN, String title_FR, String duration, String description_EN, String description_FR, String otherInformation_EN, String otherInformation_FR, Double price) {
        this.fitnessPackageIdentifier =  new FitnessPackageIdentifier();
        this.promoIdentifier = promoIdentifier;
        this.status = status;
        this.title_EN = title_EN;
        this.title_FR = title_FR;
        this.duration = duration;
        this.description_EN = description_EN;
        this.description_FR = description_FR;
        this.otherInformation_EN = otherInformation_EN;
        this.otherInformation_FR = otherInformation_FR;
        this.price = price;
    }
}
