package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;

import java.util.List;

public interface InvoiceService {

    List<InvoiceResponseModel> getAllInvoices(String invoiceid, String userid, String username, String status, String paymenttype);

    InvoiceResponseModel addInvoice(InvoiceRequestModel invoiceRequestModel);


   List<InvoiceResponseModel> getAllInvoiceByUserId(String userId);

InvoiceResponseModel getInvoiceById(String invoiceId);
    void removeInvoice( String invoiceId);
}
