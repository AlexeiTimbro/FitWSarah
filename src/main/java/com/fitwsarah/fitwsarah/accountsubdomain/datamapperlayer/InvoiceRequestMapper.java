package com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer;


import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(invoiceIdentifier) ", target = "invoiceIdentifier", ignore = true)
    Invoices requestModelToEntity(InvoiceRequestModel invoiceRequestModel);
}