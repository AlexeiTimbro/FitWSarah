package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InvoiceServiceUniyTest {

    @InjectMocks
    InvoiceServiceImpl invoiceService;
    @Mock
    InvoiceRepository invoiceRepository;
    @Mock
    InvoiceResponseMapper invoiceResponseMapper;
    @Mock
    InvoiceRequestMapper invoiceRequestMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllInvoices_ShouldSucceed() {

        String invoiceId = "uuid-invoice1";
        String accountId = "uuid-account1";
        String amount = "100.00";
        String content = "content";

        Invoices invoices = new Invoices();
        invoices.getInvoiceIdentifier().setInvoiceId(invoiceId);

        List<Invoices> invoicesList = Collections.singletonList(invoices);

        when(invoiceRepository.findAll()).thenReturn(invoicesList);

        InvoiceResponseModel invoiceResponseModel = new InvoiceResponseModel("uuid-invoice1", "uuid-account1", 100.00, "content");

        List<InvoiceResponseModel> invoiceResponseModelList = Collections.singletonList(invoiceResponseModel);

        when(invoiceResponseMapper.entityListToResponseModelList(invoicesList)).thenReturn(invoiceResponseModelList);

        List<InvoiceResponseModel> actualInvoiceResponseModelList = invoiceService.getAllInvoices();

        assertEquals(invoiceResponseModelList, actualInvoiceResponseModelList);
    }
}