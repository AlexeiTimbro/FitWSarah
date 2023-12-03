package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoOffers;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.PromoOfferRequestModel;
import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer.TrainerPanel;
import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.presentationlayer.TrainerPanelRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainerPanelRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(trainerPanelIdentifier) ", target = "trainerPanelIdentifier", ignore = true)
    @Mapping( expression = "java(adminPanelIdentifier) ", target = "adminPanelIdentifier", ignore = true)
    TrainerPanel requestModelToEntity(TrainerPanelRequestModel trainerPanelRequestModel);
}
