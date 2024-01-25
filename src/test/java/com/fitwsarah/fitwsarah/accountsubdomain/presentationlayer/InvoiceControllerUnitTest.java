package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.JsonPathAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceControllerUnitTest {

    @Mock
    InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    InvoiceResponseModel invoice1 = new InvoiceResponseModel("uuid-invoice1", "uuid-account1", 100.00, "content");
    InvoiceResponseModel invoice2 = new InvoiceResponseModel("uuid-invoice2", "uuid-account2", 200.00, "content");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllInvoices_ShouldReturnInvoices() {

        List<InvoiceResponseModel> invoiceResponseModelList = Arrays.asList(
                invoice1,
                invoice2
        );

        when(invoiceService.getAllInvoices()).thenReturn(invoiceResponseModelList);

        List<InvoiceResponseModel> actual = invoiceController.getAllInvoices();

        assertEquals(invoiceResponseModelList, actual);
    }
}