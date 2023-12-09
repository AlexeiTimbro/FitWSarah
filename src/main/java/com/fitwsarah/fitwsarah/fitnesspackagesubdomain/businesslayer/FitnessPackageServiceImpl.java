package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageRequestMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.utils.entityDTOutils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FitnessPackageServiceImpl implements FitnessPackageService{

    private FitnessPackageRepository fitnessPackageRepository;
    private FitnessPackageResponseMapper fitnessPackageResponseMapper;
    private FitnessPackageRequestMapper fitnessPackageRequestMapper;


    @Override
    public Flux<FitnessPackageResponseModel> getAllFitnessPackages(){
        return fitnessPackageRepository.findAll()
                .map(entityDTOutils::toFitnessPackageResponseDTO);
    }

    @Override
    public Mono<FitnessPackageResponseModel> getFitnessPackageByFitnessPackageId(String fitnessPackageId) {
        return null;
    }

    @Override
    public Mono<FitnessPackageResponseModel> addFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId) {
        return null;
    }

    @Override
    public Mono<FitnessPackageResponseModel> updateFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId) {
        return null;
    }

    @Override
    public Mono<Void> removeFitnessPackage(String fitnessPackageId) {
    return null;
    }
}
