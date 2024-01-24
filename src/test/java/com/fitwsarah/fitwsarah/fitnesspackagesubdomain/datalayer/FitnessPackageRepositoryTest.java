package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class FitnessPackageRepositoryTest {
    @Autowired
    private FitnessPackageRepository fitnessPackageRepository;

    private String savedServiceId;

    @BeforeEach
    public void setUp() {
        FitnessPackage fitnessPackage = new FitnessPackage();
        FitnessPackageIdentifier identifier = new FitnessPackageIdentifier();
        fitnessPackage.setFitnessPackageIdentifier(identifier);

        // Save the appointment and get the saved ID
        FitnessPackage savedService = fitnessPackageRepository.save(fitnessPackage);
        savedServiceId = savedService.getFitnessPackageIdentifier().getServiceId();
    }

    @AfterEach
    public void tearDown() {
        // Delete all appointments after each test
        fitnessPackageRepository.deleteAll();
    }

    @Test
    public void whenFindByServiceId_thenReturnFitnessPackage() {
        // Arrange
        assertNotNull(savedServiceId);

        // Act
        FitnessPackage found = fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(savedServiceId);

        // Assert
        assertNotNull(found);
        assertEquals(savedServiceId, found.getFitnessPackageIdentifier().getServiceId());
    }

    @Test
    public void whenFindByNonExistentServiceId_thenReturnNull() {
        // Arrange
        String nonExistentServiceId = "nonExistentId";

        // Act
        FitnessPackage found = fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(nonExistentServiceId);

        // Assert
        assertNull(found);
    }



    @Test
    public void whenFindByNonExistentServiceIdAndPromoId_thenReturnNull() {
        // Arrange
        String nonExistentServiceId = "nonExistentId";
        String promoId = "promoId";

        // Act
        FitnessPackage found = fitnessPackageRepository.findFitnessPackageByFitnessPackageIdentifier_ServiceIdAndPromoIdentifier_PromoId(nonExistentServiceId, promoId);

        // Assert
        assertNull(found);
    }

    @Test
    public void whenFindByServiceIdAndNonExistentPromoId_thenReturnNull() {
        // Arrange
        String promoId = "nonExistentId";

        // Act
        FitnessPackage found = fitnessPackageRepository.findFitnessPackageByFitnessPackageIdentifier_ServiceIdAndPromoIdentifier_PromoId(savedServiceId, promoId);

        // Assert
        assertNull(found);
    }

}