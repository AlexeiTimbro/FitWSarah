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
    void getAllInvoices_ShouldReturnInvoices() {
        // Arrange
        Invoices invoice = new Invoices(); // Create a mock invoice
        List<Invoices> invoicesList = Collections.singletonList(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoicesList);

        InvoiceResponseModel invoiceResponseModel = new InvoiceResponseModel("sd", "sad",100.00,"dsdsa","dsadsa");
        List<InvoiceResponseModel> invoiceResponseModelList = Collections.singletonList(invoiceResponseModel);
        when(invoiceResponseMapper.entityListToResponseModelList(invoicesList)).thenReturn(invoiceResponseModelList);

        // Act
        List<InvoiceResponseModel> actualInvoiceResponseModelList = invoiceService.getAllInvoices();

        // Assert
        assertEquals(invoiceResponseModelList, actualInvoiceResponseModelList);
    }
}