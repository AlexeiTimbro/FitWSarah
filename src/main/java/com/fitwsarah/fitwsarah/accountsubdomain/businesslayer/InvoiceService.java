package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import com.fitwsarah.fitwsarah.feeedbacksubdomain.presentationlayer.FeedbackResponseModel;

import java.util.List;

public interface InvoiceService {

    List<InvoiceResponseModel> getAllInvoices();

    InvoiceResponseModel addInvoice(InvoiceRequestModel invoiceRequestModel);
}
