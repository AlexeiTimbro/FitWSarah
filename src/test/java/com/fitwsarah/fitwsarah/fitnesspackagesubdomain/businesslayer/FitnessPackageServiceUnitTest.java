package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.appointmentsubdomain.businesslayer.AppointmentServiceImpl;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.AppointmentRepository;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoIdentifier;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class FitnessPackageServiceUnitTest {

    @InjectMocks
    FitnessPackageServiceImpl fitnessPackageService;

    @Mock
    FitnessPackageRepository fitnessPackageRepository;

    @Mock
    private FitnessPackageResponseMapper fitnessPackageResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getFitnessPackageByServiceId_Should_Succeed() {


        String serviceId = "99a836ab-8f83-4e63-b266-3f56b1396df4";


        FitnessPackage fitnessPackage = new FitnessPackage();
        fitnessPackage.getFitnessPackageIdentifier().setServiceId(serviceId);


        FitnessPackageResponseModel responseModel = new FitnessPackageResponseModel("99a836ab-8f83-4e63-b266-3f56b1396df4", "99a836ab-8f83-4e63-b266-3f56b1396df4", "99a836ab-8f83-4e63-b266-3f56b1396df4", "99a836ab-8f83-4e63-b266-3f56b1396df4", "99a836ab-8f83-4e63-b266-3f56b1396df4","s", 1.1);


        when(fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(serviceId)).thenReturn(fitnessPackage);


        // Use the appointmentResponseMapper mock to return the responseModel when mapping the appointment to response model

        when(fitnessPackageResponseMapper.entityToResponseModel(fitnessPackage)).thenReturn(responseModel);


        FitnessPackageResponseModel result = fitnessPackageService.getFitnessPackageByFitnessPackageId(serviceId);


        assertEquals(serviceId, result.getServiceId());
    }




}