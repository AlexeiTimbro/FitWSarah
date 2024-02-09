package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceStatus;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<InvoiceResponseModel> getAllInvoices(String invoiceid, String userid, String username, String status, String paymenttype) {
      
        Stream<Invoices> filteredStream = invoiceRepository.findAll().stream();

        if (invoiceid != null) {
            filteredStream = filteredStream.filter(invoice -> invoice.getInvoiceIdentifier().getInvoiceId().startsWith(invoiceid));
        }

        if (userid != null) {
            filteredStream = filteredStream.filter(invoice -> invoice.getUserId().startsWith(userid));
        }

        if (username != null) {
            filteredStream = filteredStream.filter(invoice -> invoice.getUsername().startsWith(username));
        }

        if (status != null) {
            InvoiceStatus invoiceStatus = InvoiceStatus.valueOf(status);
            filteredStream = filteredStream.filter(invoice -> invoice.getStatus() == invoiceStatus);
        }

        if (paymenttype != null) {
            filteredStream = filteredStream.filter(invoice -> invoice.getPaymentType().startsWith(paymenttype));
        }


        Set<Invoices> filteredAccounts = filteredStream.collect(Collectors.toSet());


        return invoiceResponseMapper.entityListToResponseModelList(filteredAccounts.stream()
                .sorted(Comparator.comparing(invoices -> invoices.getInvoiceIdentifier().getInvoiceId()))
                .toList());
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
