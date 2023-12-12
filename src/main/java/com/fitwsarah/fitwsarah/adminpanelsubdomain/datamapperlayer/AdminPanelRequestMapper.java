package com.fitwsarah.fitwsarah.adminpanelsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.datalayer.AdminPanel;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer.AdminPanelRequestModel;
import com.fitwsarah.fitwsarah.adminpanelsubdomain.presentationlayer.AdminPanelResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminPanelRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(adminPanelIdentifier) ", target = "adminPanelIdentifier", ignore = true)
    AdminPanel requestModelToEntity(AdminPanelRequestModel adminPanelRequestModel);
}
