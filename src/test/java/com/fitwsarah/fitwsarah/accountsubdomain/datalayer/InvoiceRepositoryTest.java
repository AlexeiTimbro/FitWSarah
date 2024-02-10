package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;

    private List<Invoices> savedInvoices;

    @BeforeEach
    public void setUp() {
        Invoices invoices1 = new Invoices();
        invoices1.setAccountId("existingAccountId");
        invoices1.setUserId("existingUserId");
        invoices1.setUsername("existingUserName");
        invoices1.setStatus(InvoiceStatus.PENDING);
        invoices1.setDate(LocalDateTime.MAX);
        invoices1.setDueDate(LocalDateTime.MAX);
        invoices1.setPaymentType("Credit Card");
        invoices1.setPrice(100.00);


        savedInvoices = invoiceRepository.saveAll(Arrays.asList(invoices1));
    }

    @Test
    public void findInvoicesByUserIdReturnsExpectedResultWhenNotesExist() {
        String userId = "existingUserId";
        List<Invoices> actualNotes = invoiceRepository.findInvoicesByUserId(userId);

        assertEquals(savedInvoices, actualNotes);
    }

    @Test
    public void findInvoicesByUserIdReturnsEmptyListWhenNoNotesExist() {
        String userId = "nonExistingUserId";
        invoiceRepository.deleteAll();

        List<Invoices> actualNotes = invoiceRepository.findInvoicesByUserId(userId);

        assertEquals(Collections.emptyList(), actualNotes);
    }

    @Test
    public void findInvoicesByUserIdReturnsEmptyListWhenUserIdIsNull() {
        String userId = null;
        List<Invoices> actualNotes = invoiceRepository.findInvoicesByUserId(userId);
        assertEquals(Collections.emptyList(), actualNotes);
    }
}