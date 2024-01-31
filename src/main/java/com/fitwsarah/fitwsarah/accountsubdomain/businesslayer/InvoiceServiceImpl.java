package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private InvoiceResponseMapper invoiceResponseMapper;
    private InvoiceRequestMapper invoiceRequestMapper;
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceResponseMapper invoiceResponseMapper, InvoiceRequestMapper invoiceRequestMapper, InvoiceRepository invoiceRepository) {
        this.invoiceResponseMapper = invoiceResponseMapper;
        this.invoiceRequestMapper = invoiceRequestMapper;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<InvoiceResponseModel> getAllInvoices() {
        return invoiceResponseMapper.entityListToResponseModelList(invoiceRepository.findAll());
    }

    @Override
    public InvoiceResponseModel addInvoice(InvoiceRequestModel invoiceRequestModel) {

        Invoices invoice = invoiceRequestMapper.requestModelToEntity(invoiceRequestModel);
        Invoices saved = invoiceRepository.save(invoice);
        return invoiceResponseMapper.entityToResponseModel(saved);
    }


    @Override
    public List<InvoiceResponseModel> getAllInvoiceByUserId(String userId) {
        return invoiceResponseMapper.entityListToResponseModelList(invoiceRepository.findInvoicesByUserId(userId));
    }
}
