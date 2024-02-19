package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FitnessPackageTest {
    @Test
    void fitnessPackageConstructorTest() {
        Status status = Status.ACTIVE;
        String title_EN = "Title EN";
        String title_FR = "Title FR";
        String duration = "30 minutes";
        String description_EN = "Description EN";
        String description_FR = "Description FR";
        String otherInformation_EN = "Other Information EN";
        String otherInformation_FR = "Other Information FR";
        Double price = 100.00;

        FitnessPackage fitnessPackage = new FitnessPackage(status, title_EN, title_FR, duration, description_EN, description_FR, otherInformation_EN, otherInformation_FR, price);

        assertEquals(status, fitnessPackage.getStatus());
        assertEquals(title_EN, fitnessPackage.getTitle_EN());
        assertEquals(title_FR, fitnessPackage.getTitle_FR());
        assertEquals(duration, fitnessPackage.getDuration());
        assertEquals(description_EN, fitnessPackage.getDescription_EN());
        assertEquals(description_FR, fitnessPackage.getDescription_FR());
        assertEquals(otherInformation_EN, fitnessPackage.getOtherInformation_EN());
        assertEquals(otherInformation_FR, fitnessPackage.getOtherInformation_FR());
        assertEquals(price, fitnessPackage.getPrice());
    }

}