package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.InvoiceService;
import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class InvoiceControllerUnitTest {

    @Mock
    InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    InvoiceResponseModel invoice1 = new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);
    InvoiceResponseModel invoice2 = new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /*@Test
    void getAllInvoices_ShouldReturnInvoices() {

        List<InvoiceResponseModel> invoiceResponseModelList = Arrays.asList(
                invoice1,
                invoice2
        );

        when(invoiceService.getAllInvoices()).thenReturn(invoiceResponseModelList);

        List<InvoiceResponseModel> actual = invoiceController.getAllInvoices();

        assertEquals(invoiceResponseModelList, actual);
    }
*/
    @Test
    void addInvoice_ShouldReturnInvoice() {

        InvoiceRequestModel invoiceRequestModel = new InvoiceRequestModel("uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);

        when(invoiceService.addInvoice(invoiceRequestModel)).thenReturn(invoice1);

        InvoiceResponseModel actual = invoiceController.addInvoice(invoiceRequestModel);

        assertEquals(invoice1, actual);
    }


    @Test
    public void getInvoicesByUserIdTest() {
        String userId = "testUserId";
        InvoiceResponseModel invoiceResponseModel = new InvoiceResponseModel("inv-uuid-1", "uuid-acc1", "1", "johnsmith", InvoiceStatus.COMPLETED, LocalDateTime.now(), LocalDateTime.now(), "Credit Card", 100.00);
        List<InvoiceResponseModel> expectedResponse = Arrays.asList(invoiceResponseModel);

        when(invoiceService.getAllInvoiceByUserId(userId)).thenReturn(expectedResponse);

        List<InvoiceResponseModel> actualResponse = invoiceController.getAllInvoiceByUserId(userId);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void getAllInvoices_ShouldReturnFeedbacks() {
        List<InvoiceResponseModel> expectedFeedbacks = Arrays.asList(invoice1, invoice2);

        when(invoiceService.getAllInvoices(null, null, null, null, null)).thenReturn(expectedFeedbacks);

        List<InvoiceResponseModel> actualFeedbacks = invoiceController.getAllInvoices(null, null, null, null, null);
        Assertions.assertEquals(expectedFeedbacks, actualFeedbacks);
        assertThat(actualFeedbacks.get(0), is(invoice1));
        assertThat(actualFeedbacks.get(1), is(invoice2));
    }


    @Test
    public void removeInvoice_validInvoiceId_shouldSucceed() {
        String invoiceId = "uuid-feed1";
        doNothing().when(invoiceService).removeInvoice(invoiceId);

        ResponseEntity<Void> result = invoiceController.removeInvoice(invoiceId);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(invoiceService, times(1)).removeInvoice(invoiceId);
    }
}