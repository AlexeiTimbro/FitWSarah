package com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceResponseMapper {

    @Mapping(expression = "java(invoices.getInvoiceIndentifier().getInvoiceId())", target = "invoiceId")
    InvoiceRequestModel entityToResponseModel(Invoices invoices);
}
