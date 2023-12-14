package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;


class FitnessPackageControllerUnitTest {

    @Mock
    private FitnessPackageService fitnessPackageService;

    @InjectMocks
    private FitnessPackageController fitnessPackageController;

    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1","One On One Training", "1 hour", "Desc", "s",100.00);
    FitnessPackageResponseModel fitnessPackage2 = new FitnessPackageResponseModel("serviceID2", "promoID2", "One On One Training1", "2 hour", "Desc2", "s",200.00);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllFitnessServices_ShouldReturnFitnessPackages() {
        // Arrange
        List<FitnessPackageResponseModel> fitnessPackageList = Arrays.asList(
                fitnessPackage,
                fitnessPackage2
        );
        when(fitnessPackageService.getAllFitnessPackages()).thenReturn(fitnessPackageList);

        // Act
        List<FitnessPackageResponseModel> result = fitnessPackageController.getAllFitnessServices();

        // Assert
        assertThat(result, hasSize(fitnessPackageList.size()));
        assertThat(result, is(fitnessPackageList));
    }

    @Test
    void getFitnessPackageById_ShouldReturnFitnessPackage() {
        //Arrange
        when(fitnessPackageService.getFitnessPackageByFitnessPackageId(anyString())).thenReturn(fitnessPackage);

        //Act
        FitnessPackageResponseModel result = fitnessPackageController.getFitnessServiceById(fitnessPackage.getServiceId());

        // Assert
        assertThat(result, is(fitnessPackage));
    }


}