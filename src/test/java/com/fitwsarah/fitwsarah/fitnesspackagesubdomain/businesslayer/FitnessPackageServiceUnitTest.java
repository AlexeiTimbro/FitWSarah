package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoIdentifier;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
    @MockBean
    FitnessPackageResponseMapper fitnessPackageResponseMapper;

    PromoIdentifier promoIdentifier = new PromoIdentifier();
    FitnessPackageResponseModel fitnessPackageResponseModel1 = FitnessPackageResponseModel.builder()
            .serviceId("Service_ID1")
            .promoId(promoIdentifier.toString())
            .title("One On One Training")
            .duration("1 hour")
            .description("Desc")
            .price(100.00)
            .build();

    FitnessPackageResponseModel fitnessPackageResponseModel2 = FitnessPackageResponseModel.builder()
            .serviceId("Service_ID2")
            .promoId(promoIdentifier.toString())
            .title("One On One Training")
            .duration("1 hour")
            .description("Desc")
            .price(100.00)
            .build();
    @Test
    void getAllFitnessPackages_ShouldSucceed() {
        // Arrange
        List<FitnessPackage> fitnessPackages = new ArrayList<>();
        fitnessPackages.add(new FitnessPackage(promoIdentifier, "One On One Training", "1 hour", "Desc", 100.00));
        fitnessPackages.add(new FitnessPackage(promoIdentifier, "One On One Training", "1 hour", "Desc", 100.00));

        Mockito.when(fitnessPackageRepository.findAll()).thenReturn(fitnessPackages);

        Mockito.when(fitnessPackageResponseMapper.entityToResponseModel(fitnessPackages.get(0)))
                .thenReturn(fitnessPackageResponseModel1);
        Mockito.when(fitnessPackageResponseMapper.entityToResponseModel(fitnessPackages.get(1)))
                .thenReturn(fitnessPackageResponseModel2);

        // Act
        List<FitnessPackageResponseModel> result = fitnessPackageService.getAllFitnessPackages();

        // Assert
        assertNotNull(result, "The result should not be null.");
        assertEquals(2, result.size(), "The result list should contain two elements.");
        assertTrue(result.contains(fitnessPackageResponseModel1), "The result should contain fitnessPackageResponseModel1.");
        assertTrue(result.contains(fitnessPackageResponseModel2), "The result should contain fitnessPackageResponseModel2.");
    }
}