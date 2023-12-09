package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FitnessPackageService {

    Flux<FitnessPackageResponseModel> getAllFitnessPackages();

    Mono<FitnessPackageResponseModel> getFitnessPackageByFitnessPackageId(String fitnessPackageId);

    Mono<FitnessPackageResponseModel> addFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId);

    Mono<FitnessPackageResponseModel> updateFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId);

    Mono<Void> removeFitnessPackage(String fitnessPackageId);
}
