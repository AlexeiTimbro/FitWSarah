package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;


import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.Feedback;
import com.fitwsarah.fitwsarah.feedbacksubdomain.datalayer.State;
import com.fitwsarah.fitwsarah.feedbacksubdomain.presentationlayer.FeedbackResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageRequestMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")

class FitnessPackageServiceUnitTest {

    @InjectMocks
    FitnessPackageServiceImpl fitnessPackageService;

    @Mock
    FitnessPackageRepository fitnessPackageRepository;

    @Mock
    private FitnessPackageResponseMapper fitnessPackageResponseMapper;

    @Mock
    private FitnessPackageRequestMapper fitnessPackageRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getFitnessPackageByServiceId_Should_Succeed() {


        String serviceId = "99a836ab-8f83-4e63-b266-3f56b1396df4";


        FitnessPackage fitnessPackage = new FitnessPackage();
        fitnessPackage.getFitnessPackageIdentifier().setServiceId(serviceId);


        FitnessPackageResponseModel responseModel = new FitnessPackageResponseModel("99a836ab-8f83-4e63-b266-3f56b1396df4", "99a836ab-8f83-4e63-b266-3f56b1396df4", Status.INVISIBLE, "99a836ab-8f83-4e63-b266-3f56b1396df4", "99a836ab-8f83-4e63-b266-3f56b1396df4","s", "fdsaf", "fdsaf", "sadff", "22.00", 99.0);


        when(fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(serviceId)).thenReturn(fitnessPackage);


        // Use the appointmentResponseMapper mock to return the responseModel when mapping the appointment to response model

        when(fitnessPackageResponseMapper.entityToResponseModel(fitnessPackage)).thenReturn(responseModel);


        FitnessPackageResponseModel result = fitnessPackageService.getFitnessPackageByFitnessPackageId(serviceId);


        assertEquals(serviceId, result.getServiceId());
    }

    @Test
    public void addFitnessPackage_shouldSucceed(){
        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel(Status.INVISIBLE,"20 minutes", "sadfdf","desc","other", "fsafd", "sdfa", "22.00", 99.0);

        FitnessPackage entity = mock(FitnessPackage.class);
        FitnessPackageResponseModel mockedResponse = new FitnessPackageResponseModel("serviceId", "uuid-promo1", Status.INVISIBLE,  "20 minutes", "desc", "other", "sadfds", "fdsaf", "fdsaf", "22.00", 99.0);

        when(fitnessPackageResponseMapper.entityToResponseModel(entity)).thenReturn(mockedResponse);
        when(fitnessPackageRequestMapper.requestModelToEntity(fitnessPackageRequestModel)).thenReturn(entity);
        when(fitnessPackageRepository.save(entity)).thenReturn(entity);

        FitnessPackageResponseModel result = fitnessPackageService.addFitnessPackage(fitnessPackageRequestModel);
        assertNotNull(result);
        assertNotNull(result.getServiceId());
        assertNotNull(result.getDescription_EN());
        assertNotNull(result.getDescription_FR());
        assertNotNull(result.getPrice());
        assertNotNull(result.getTitle_EN());
        assertNotNull(result.getTitle_FR());
    }


    @Test
    public void updateFitnessPacakge_shouldSucceed(){

        String fitnessPackageId = "uuid-appt1";
        FitnessPackageRequestModel fitnessPackageRequestModel = new FitnessPackageRequestModel(Status.INVISIBLE,"20 minutes", "desc","other","sadfds", "fsafd", "sdfa", "22.00", 99.0);


        FitnessPackage existingFitnessPackage = new FitnessPackage();
        existingFitnessPackage.getFitnessPackageIdentifier().setServiceId(fitnessPackageId);
        existingFitnessPackage.setStatus(Status.INVISIBLE);

        when(fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(fitnessPackageId))
                .thenReturn(existingFitnessPackage);

        when(fitnessPackageRepository.save(any(FitnessPackage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FitnessPackageResponseModel mockedResponse = new FitnessPackageResponseModel("serviceId", "uuid-promo1", Status.INVISIBLE,  "20 minutes", "desc", "other", "sadfds", "fdsaf", "sdfa", "22.00", 99.0);

        when(fitnessPackageResponseMapper.entityToResponseModel(any(FitnessPackage.class)))
                .thenReturn(mockedResponse);

        FitnessPackageResponseModel result = fitnessPackageService.updateFitnessPackage(fitnessPackageRequestModel, fitnessPackageId);

        assertNotNull(result);
        assertEquals(fitnessPackageRequestModel.getStatus(), result.getStatus());
        assertEquals(fitnessPackageRequestModel.getTitle_EN(), result.getTitle_EN());
        assertEquals(fitnessPackageRequestModel.getTitle_FR(), result.getTitle_FR());
        assertEquals(fitnessPackageRequestModel.getDuration(), result.getDuration());
        assertEquals(fitnessPackageRequestModel.getDescription_EN(), result.getDescription_EN());
        assertEquals(fitnessPackageRequestModel.getOtherInformation_EN(), result.getOtherInformation_EN());
        assertEquals(fitnessPackageRequestModel.getOtherInformation_FR(), result.getOtherInformation_FR());
        assertEquals(fitnessPackageRequestModel.getPrice(), result.getPrice());

        Mockito.verify(fitnessPackageRepository).save(existingFitnessPackage);
    }

    @Test
    public void updateFitnessPackageStatus_shouldSucceed() {
        String serviceId = "serviceId1";
        String status = "VISIBLE";

        FitnessPackage existingFitnessPackage = new FitnessPackage();
        existingFitnessPackage.getFitnessPackageIdentifier().setServiceId(serviceId);
        existingFitnessPackage.setStatus(Status.INVISIBLE);

        FitnessPackage updatedFitnessPackage = new FitnessPackage();
        updatedFitnessPackage.getFitnessPackageIdentifier().setServiceId(serviceId);
        updatedFitnessPackage.setStatus(Status.valueOf(status));

        FitnessPackageResponseModel responseModel = new FitnessPackageResponseModel(
                "serviceId1",
                "promoId1",
                Status.VISIBLE,
                "20 minutes",
                "desc",
                "other",
                "fdsaf",
                "fdsaf",
                "fasdf",
                "22.00",
                99.0
        );

        when(fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(serviceId)).thenReturn(existingFitnessPackage);
        when(fitnessPackageRepository.save(any(FitnessPackage.class))).thenReturn(updatedFitnessPackage);
        when(fitnessPackageResponseMapper.entityToResponseModel(any(FitnessPackage.class))).thenReturn(responseModel);

        FitnessPackageResponseModel result = fitnessPackageService.updateFitnessPackageStatus(serviceId, status);

        assertNotNull(result, "Result should not be null");
        assertEquals(Status.VISIBLE, result.getStatus());
    }

    @Test
    void getAllFitnessPackagesTest_withServiceIdAndStatus() {
        // Arrange
        String serviceId = "serviceId1";
        String status = "VISIBLE";

        FitnessPackage fitnessPackage1 = new FitnessPackage();
        fitnessPackage1.getFitnessPackageIdentifier().setServiceId("serviceId1");
        fitnessPackage1.setStatus(Status.VISIBLE);

        FitnessPackage fitnessPackage2 = new FitnessPackage();
        fitnessPackage2.getFitnessPackageIdentifier().setServiceId("serviceId2");
        fitnessPackage2.setStatus(Status.INVISIBLE);

        when(fitnessPackageRepository.findAll()).thenReturn(Arrays.asList(fitnessPackage1, fitnessPackage2));

        FitnessPackageResponseModel responseModel1 = new FitnessPackageResponseModel("serviceId1", "promoId1", Status.VISIBLE, "title1", "title1", "duration1", "desc1", "desc1", "other1", "other1", 100.0);
        FitnessPackageResponseModel responseModel2 = new FitnessPackageResponseModel("serviceId2", "promoId2", Status.INVISIBLE, "title2", "title2", "duration2", "desc2", "desc2", "other2", "other2", 200.0);

        when(fitnessPackageResponseMapper.entityToResponseModel(fitnessPackage1)).thenReturn(responseModel1);
        when(fitnessPackageResponseMapper.entityToResponseModel(fitnessPackage2)).thenReturn(responseModel2);

        // Act
        List<FitnessPackageResponseModel> result = fitnessPackageService.getAllFitnessPackages(serviceId, status);

        // Assert
        assertEquals(0, result.size());
    }


}