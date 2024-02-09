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
    public List<InvoiceResponseModel> getAllInvoices(String invoiceid, String userid, String username, String state, String paymenttype) {
        Set<Invoices> filteredAccounts = new HashSet<>();

        if (invoiceid != null) {
            filteredAccounts.addAll(invoiceRepository.findInvoicesByInvoiceIdentifier_InvoiceIdStartingWith(invoiceid));
        }

        else if (userid != null) {
            filteredAccounts.addAll(invoiceRepository.findInvoicesByUserIdStartingWith(userid));
        }
        else if (username != null) {
            filteredAccounts.addAll(invoiceRepository.findAllInvoicesByUsernameStartingWith(username));
        } else if (state != null) {
            filteredAccounts.addAll(invoiceRepository.findInvoicesByStatus(InvoiceStatus.valueOf(state)));
        } else if (paymenttype != null) {
            filteredAccounts.addAll(invoiceRepository.findInvoicesByPaymentTypeStartingWith(paymenttype));
        }
        else {
            filteredAccounts.addAll(invoiceRepository.findAll());
        }

        return invoiceResponseMapper.entityListToResponseModelList(filteredAccounts.stream().sorted(Comparator.comparing(invoices -> invoices.getInvoiceIdentifier().getInvoiceId())).toList());

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
