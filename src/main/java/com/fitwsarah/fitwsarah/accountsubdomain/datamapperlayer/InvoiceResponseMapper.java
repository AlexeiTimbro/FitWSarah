package com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceResponseMapper {

    @Mapping(expression = "java(invoices.getInvoiceIdentifier().getInvoiceId())", target = "invoiceId")
    InvoiceResponseModel entityToResponseModel(Invoices invoices);

    List<InvoiceResponseModel> entityListToResponseModelList(List<Invoices> invoices);
}
