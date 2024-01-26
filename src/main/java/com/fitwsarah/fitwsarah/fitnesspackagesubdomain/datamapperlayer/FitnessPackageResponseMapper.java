package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FitnessPackageResponseMapper {
    @Mapping(expression = "java(fitnessPackage.getFitnessPackageIdentifier().getServiceId())", target = "serviceId")
    FitnessPackageResponseModel entityToResponseModel(FitnessPackage fitnessPackage);

    List<FitnessPackageResponseModel> entityListToResponseModelList(List<FitnessPackage> fitnessPackages);
}
