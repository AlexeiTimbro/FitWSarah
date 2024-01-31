package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.AccountRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceStatus;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.AccountResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Status;
import com.fitwsarah.fitwsarah.appointmentsubdomain.presentationlayer.AppointmentResponseModel;
import com.fitwsarah.fitwsarah.coachnotesubdomain.presentationlayer.CoachNoteResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
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
        Invoices invoice = new Invoices();
        List<Invoices> invoicesList = Collections.singletonList(invoice);
        when(invoiceRepository.findAll()).thenReturn(invoicesList);

        InvoiceResponseModel invoiceResponseModel = new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);
        List<InvoiceResponseModel> invoiceResponseModelList = Collections.singletonList(invoiceResponseModel);
        when(invoiceResponseMapper.entityListToResponseModelList(invoicesList)).thenReturn(invoiceResponseModelList);

        // Act
        List<InvoiceResponseModel> actualInvoiceResponseModelList = invoiceService.getAllInvoices();

        // Assert
        assertEquals(invoiceResponseModelList, actualInvoiceResponseModelList);
    }

    @Test
    void createInvoice_ShouldReturnInvoice() {
        // Arrange
        Invoices invoice = new Invoices();
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        InvoiceResponseModel invoiceResponseModel = new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);
        InvoiceRequestModel invoiceRequestModel = new InvoiceRequestModel("uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);
        when(invoiceRequestMapper.requestModelToEntity(invoiceRequestModel)).thenReturn(invoice);
        when(invoiceResponseMapper.entityToResponseModel(invoice)).thenReturn(invoiceResponseModel);

        // Act
        InvoiceResponseModel actualInvoiceResponseModel = invoiceService.addInvoice(invoiceRequestModel);

        // Assert
        assertEquals(invoiceResponseModel, actualInvoiceResponseModel);
    }



    @Test
    public void getCoachNoteByUserIdReturnsExpectedResult() {
        String userId = "testUserId";
        List<InvoiceResponseModel> expectedResponse = Collections.singletonList(new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00));
        when(invoiceRepository.findInvoicesByUserId(userId)).thenReturn(Collections.emptyList());
        when(invoiceResponseMapper.entityListToResponseModelList(Collections.emptyList())).thenReturn(expectedResponse);

        List<InvoiceResponseModel> actualResponse = invoiceService.getAllInvoiceByUserId(userId);

        assertEquals(expectedResponse, actualResponse);
    }
}