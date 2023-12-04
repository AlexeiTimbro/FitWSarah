package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoIdentifier;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FitnessPackageServiceUnitTest {
    @Autowired
    FitnessPackageService fitnessPackageService;
    @MockBean
    FitnessPackageRepository fitnessPackageRepository;
    PromoIdentifier promoIdentifier = new PromoIdentifier();
    FitnessPackage fitnessPackage = new FitnessPackage(promoIdentifier, "One On One Training", "1 hour", "Desc", 100.00);
    FitnessPackage fitnessPackage2 = new FitnessPackage(promoIdentifier, "One On One Training1", "2 hour", "Desc2", 200.00);

    List<FitnessPackage> fitnessPackages = new ArrayList<>();
    @BeforeEach
    void setUp() {
        fitnessPackageRepository.deleteAll();
        fitnessPackageRepository.save(fitnessPackage);
        fitnessPackageRepository.save(fitnessPackage2);
        fitnessPackages.add(fitnessPackage);
        fitnessPackages.add(fitnessPackage2);
    }

    @Test
    void getAllFitnessPackages_ShouldSucceed() {


        Mockito.when(fitnessPackageRepository.findAll()).thenReturn(fitnessPackages);

        // Act
        List<FitnessPackageResponseModel> result = fitnessPackageService.getAllFitnessPackages();

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertEquals(2, result.size(), "The result list should contain two elements.");
        assertEquals(fitnessPackage.getTitle(), result.get(0).getTitle());
        assertEquals(fitnessPackage.getPrice(), result.get(0).getPrice());
        assertEquals(fitnessPackage.getDescription(), result.get(0).getDescription());
        assertEquals(fitnessPackage.getDuration(), result.get(0).getDuration());
        assertEquals(fitnessPackage.getPrice(), result.get(0).getPrice());
        assertEquals(fitnessPackage2.getTitle(), result.get(1).getTitle());
        assertEquals(fitnessPackage2.getPrice(), result.get(1).getPrice());
        assertEquals(fitnessPackage2.getDescription(), result.get(1).getDescription());
        assertEquals(fitnessPackage2.getDuration(), result.get(1).getDuration());
        assertEquals(fitnessPackage2.getPrice(), result.get(1).getPrice());
    }
}