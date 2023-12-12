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
    private FitnessPackageServiceImpl fitnessPackageService;

    @Mock
    private FitnessPackageRepository fitnessPackageRepository;

    @Mock
    private FitnessPackageResponseMapper fitnessPackageResponseMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllFitnessPackages_returnsNonEmptyList_whenPackagesExist() {
        FitnessPackage fitnessPackage = new FitnessPackage();
        FitnessPackageResponseModel responseModel = new FitnessPackageResponseModel("serviceID1", "promoID1","One On One Training", "1 hour", "Desc", 100.00);

        when(fitnessPackageRepository.findAll()).thenReturn(Arrays.asList(fitnessPackage));
        when(fitnessPackageResponseMapper.entityListToResponseModelList(Arrays.asList(fitnessPackage))).thenReturn(Arrays.asList(responseModel));

        List<FitnessPackageResponseModel> result = fitnessPackageService.getAllFitnessPackages();

        assertEquals(1, result.size());
    }
}