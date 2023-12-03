package com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.datalayer.TrainerPanel;
import com.fitwsarah.fitwsarah.personaltrainerpanelsubdomain.presentationlayer.TrainerPanelResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerPanelResponseMapper {
    @Mapping(expression = "java(trainerPanel.getTrainerPanelIdentifier().getAvailabilityId())", target = "availabilityId")
    @Mapping(expression = "java(trainerPanel.getAdminPanelIdentifier().getAdminId())", target = "adminId")
    TrainerPanelResponseModel entityToResponseModel(TrainerPanel trainerPanel);

    List<TrainerPanelResponseModel> entityListToResponseModelList(List<TrainerPanel> trainerPanels);
}
