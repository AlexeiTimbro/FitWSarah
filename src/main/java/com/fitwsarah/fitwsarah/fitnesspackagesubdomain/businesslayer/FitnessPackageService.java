package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;

import java.util.List;

public interface FitnessPackageService {

    List<FitnessPackageResponseModel> getAllFitnessPackages();

    FitnessPackageResponseModel getFitnessPackageByFitnessPackageId(String fitnessPackageId);

    FitnessPackageResponseModel addFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId);

    FitnessPackageResponseModel updateFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId);

    void removeFitnessPackage(String fitnessPackageId);
}
