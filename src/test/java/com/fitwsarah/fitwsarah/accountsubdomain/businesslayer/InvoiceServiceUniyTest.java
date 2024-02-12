package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceRepository;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceStatus;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Invoices;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceRequestMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer.InvoiceResponseMapper;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
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

    /*@Test
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
*/
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
    void getAllInvoices_ShouldFilterAndReturnInvoices() {
        // Arrange
        String invoiceId = "inv1";
        String userId = "user1";
        String userName = "John";
        String status = "COMPLETED";
        String paymentType = "Credit Card";

        Invoices invoice1 = new Invoices();
        List<Invoices> invoicesList = Collections.singletonList(invoice1);
        when(invoiceRepository.findAll()).thenReturn(invoicesList);

        InvoiceResponseModel invoiceResponseModel = new InvoiceResponseModel(invoiceId, userId, "1", userName, InvoiceStatus.valueOf(status), LocalDateTime.now(), LocalDateTime.now(), paymentType, 100.00);
        List<InvoiceResponseModel> expectedResponse = Collections.singletonList(invoiceResponseModel);
        when(invoiceResponseMapper.entityListToResponseModelList(anyList())).thenReturn(expectedResponse);

        // Act
        List<InvoiceResponseModel> actualResponse = invoiceService.getAllInvoices(invoiceId, userId, userName, status, paymentType);

        // Assert
        assertNotNull(actualResponse);
        assertFalse(actualResponse.isEmpty());
        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0), actualResponse.get(0));

        // Verify interactions
        verify(invoiceRepository).findAll();
        verify(invoiceResponseMapper).entityListToResponseModelList(anyList());
    }


    @Test
    public void getCoachNoteByUserIdReturnsExpectedResult() {
        String userId = "testUserId";
        List<InvoiceResponseModel> expectedResponse = Collections.singletonList(new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.OVERDUE, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00));
        when(invoiceRepository.findInvoicesByUserId(userId)).thenReturn(Collections.emptyList());
        when(invoiceResponseMapper.entityListToResponseModelList(Collections.emptyList())).thenReturn(expectedResponse);

        List<InvoiceResponseModel> actualResponse = invoiceService.getAllInvoiceByUserId(userId);

        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void removeInvoices_invalidInvoiceId_shouldFail(){
        String invoiceId = "invalid-invoice-id1";

        when(invoiceRepository.findInvoicesByInvoiceIdentifier_InvoiceId(invoiceId)).thenReturn(null);

        invoiceService.removeInvoice(invoiceId);

        verify(invoiceRepository, never()).delete(any(Invoices.class));
    }

    @Test
    public void removeInvoice_validInvoiceId_shouldSucceed(){
        String invoiceId = "invoice-id1";
        Invoices entity = mock(Invoices.class);

        when(invoiceRepository.findInvoicesByInvoiceIdentifier_InvoiceId(invoiceId)).thenReturn(entity);

        invoiceService.removeInvoice(invoiceId);

        verify(invoiceRepository, times(1)).delete(entity);
    }


}