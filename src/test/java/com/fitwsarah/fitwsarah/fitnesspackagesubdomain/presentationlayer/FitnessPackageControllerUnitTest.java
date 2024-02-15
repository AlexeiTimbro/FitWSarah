package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.State;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer.FitnessPackageService;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.Status;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
class FitnessPackageControllerUnitTest {

    @Mock
    private FitnessPackageService fitnessPackageService;

    @InjectMocks
    private FitnessPackageController fitnessPackageController;

    FitnessPackageResponseModel fitnessPackage = new FitnessPackageResponseModel("serviceID1", "promoID1", Status.INVISIBLE, "1 hour", "Desc", "s", "asdf", "asdf", "asdf", "asdf", 22.00);
    FitnessPackageResponseModel fitnessPackage2 = new FitnessPackageResponseModel("serviceID2", "promoID2", Status.INVISIBLE, "2 hour", "Desc2", "s", "asdf", "asdf", "asdf", "asdf", 22.00);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

   /* @Test
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

    */

    @Test
    void getFitnessPackageById_ShouldReturnFitnessPackage() {
        //Arrange
        when(fitnessPackageService.getFitnessPackageByFitnessPackageId(anyString())).thenReturn(fitnessPackage);

        //Act
        FitnessPackageResponseModel result = fitnessPackageController.getFitnessServiceById(fitnessPackage.getServiceId());

        // Assert
        assertThat(result, is(fitnessPackage));
    }

    @Test
    void addFitnessPackage_shouldSucceed() {
        String serviceId = "uuid-serv1";
        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel(Status.INVISIBLE, "20 minutes", "desc", "other", "fdsaf", "fdsaf", "fasdf", "22.00", 99.0);
        FitnessPackageResponseModel expectedResponse = new FitnessPackageResponseModel(serviceId, "uuid-promo1", Status.INVISIBLE, "20 minutes", "desc", "other", "sadfds", "fdsaf", "fdsaf", "22.00", 99.0);
        when(fitnessPackageService.addFitnessPackage(fitnessPackageRequestModel)).thenReturn(expectedResponse);

        ResponseEntity<FitnessPackageResponseModel> result = fitnessPackageController.addFitnessService(fitnessPackageRequestModel);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
    }

    @Test
    void updateFitnessPackage_shouldSucceed() {
        String serviceId = "uuid-serv1";
        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel(Status.INVISIBLE, "20 minutes", "desc", "other", "fdsaf", "fdsaf", "fasdf", "22.00", 99.0);
        FitnessPackageResponseModel expectedResponse = new FitnessPackageResponseModel(serviceId, "uuid-promo1", Status.INVISIBLE, "20 minutes", "desc", "other", "sadfds", "fdsaf", "fdsaf", "22.00", 99.0);

        when(fitnessPackageService.updateFitnessPackage(fitnessPackageRequestModel, serviceId)).thenReturn(expectedResponse);

        FitnessPackageResponseModel result = fitnessPackageService.updateFitnessPackage(fitnessPackageRequestModel, serviceId);

        assertNotNull(result);
        Assert.assertEquals(expectedResponse, result);


        verify(fitnessPackageService).updateFitnessPackage(fitnessPackageRequestModel, serviceId);
        verifyNoMoreInteractions(fitnessPackageService);
    }


    @Test
    void updateFeedbackStatus_shouldSucceed() {
        String serviceId = "uuid-feed1";
        String status = "VISIBLE";

        FitnessPackageResponseModel expectedResponse = new FitnessPackageResponseModel(serviceId, "uuid-promo1", Status.INVISIBLE, "20 minutes", "desc", "other", "sadfds", "fdsaf", "fdsaf", "22.00", 99.0);
        when(fitnessPackageService.updateFitnessPackageStatus(serviceId, status)).thenReturn(expectedResponse);

        FitnessPackageResponseModel result = fitnessPackageController.updateFitnessServiceStatus(serviceId, status);

        assertEquals(expectedResponse, result);
    }


    @Test
    void updateFitnessPackageStatus_shouldSucceed() {
        String serviceId = "uuid-serv1";
        String status = "VISIBLE";
        FitnessPackageResponseModel expectedResponse = new FitnessPackageResponseModel(serviceId, "uuid-promo1", Status.INVISIBLE, "20 minutes", "desc", "other", "sadfds", "fdsaf", "fdsaf", "22.00", 99.0);

        when(fitnessPackageService.updateFitnessPackageStatus(serviceId, status)).thenReturn(expectedResponse);

        FitnessPackageResponseModel result = fitnessPackageService.updateFitnessPackageStatus(serviceId, status);

        assertNotNull(result);
        Assert.assertEquals(expectedResponse, result);
    }
}