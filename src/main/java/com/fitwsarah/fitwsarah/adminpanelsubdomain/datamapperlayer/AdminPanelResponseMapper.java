package com.fitwsarah.fitwsarah.adminpanelsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanel;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer.AdminPanelResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminPanelResponseMapper {
    @Mapping(expression = "java(admin.getAdminPanelIdentifier().getAdminId())", target = "adminId")
    AdminPanelResponseModel entityToResponseModel(AdminPanel admin);

    List<AdminPanelResponseModel> entityListToResponseModelList(List<AdminPanel> adminPanels ) ;
}
