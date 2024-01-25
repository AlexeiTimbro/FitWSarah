package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceControllerUnitTest {

    @Mock
    InvoiceService invoiceService;

    @InjectMocks
    private AccountController accountController;

    AccountResponseModel account1 = new AccountResponseModel("uuid-1", "appt1", "adms", "uuid-admin1", "Scheduled");
    AccountResponseModel account2 = new AccountResponseModel("uuid-2", "appt1", "adms", "uuid-admin1", "Scheduled");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}