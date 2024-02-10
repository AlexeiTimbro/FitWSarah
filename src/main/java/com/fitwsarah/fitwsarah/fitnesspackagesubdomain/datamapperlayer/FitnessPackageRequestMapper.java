package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FitnessPackageRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(fitnessPackageIdentifier) ", target = "fitnessPackageIdentifier", ignore = true)
    FitnessPackage requestModelToEntity(FitnessPackageRequestModel fitnessPackageRequestModel);
}
