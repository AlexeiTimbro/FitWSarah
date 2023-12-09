package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        FitnessPackageResponseModel responseModel = new FitnessPackageResponseModel("serviceID1", "promoID1", "One On One Training", "1 hour", "Desc", 100.00);

        when(fitnessPackageRepository.findAll()).thenReturn(Flux.just(fitnessPackage));

        Flux<FitnessPackageResponseModel> resultFlux = fitnessPackageService.getAllFitnessPackages();

        // Block and collect the result from the Flux
        List<FitnessPackageResponseModel> result = resultFlux.collectList().block();

        assertEquals(1, result.size());
    }
}
