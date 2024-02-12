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

    Invoices savedInvoices;

    private String savedInvoiceId;
    private String savedUserId;
    @BeforeEach
    public void setUp() {
        Invoices invoices1 = new Invoices();
        InvoiceIndentifier identifier = new InvoiceIndentifier();
        identifier.setInvoiceId("generatedAccountId");
        invoices1.setAccountId("existingAccountId");
        invoices1.setUserId("existingUserId");
        invoices1.setUsername("existingUserName");
        invoices1.setStatus(InvoiceStatus.PENDING);
        invoices1.setDate(LocalDateTime.MAX);
        invoices1.setDueDate(LocalDateTime.MAX);
        invoices1.setPaymentType("Credit Card");
        invoices1.setPrice(100.00);


        savedInvoices = invoiceRepository.save(invoices1);
        savedInvoiceId = savedInvoices.getInvoiceIdentifier().getInvoiceId();
        savedUserId = savedInvoices.getUserId();
    }


    @Test
    public void whenFindByInvoiceUserId_thenReturnInvoice() {
        // Arrange
        assertNotNull(savedUserId);

        // Act
        List<Invoices> found = invoiceRepository.findInvoicesByUserId(savedUserId);

        // Assert
        assertNotNull(found);
        assertEquals(savedUserId, found.get(0).getUserId());
    }

    @Test
    public void whenFindByInvoiceId_thenReturnInvoice() {
        // Arrange
        assertNotNull(savedInvoiceId);

        // Act
        Invoices found = invoiceRepository.findInvoicesByInvoiceIdentifier_InvoiceId(savedInvoiceId);

        // Assert
        assertNotNull(found);
        assertEquals(savedInvoiceId, found.getInvoiceIdentifier().getInvoiceId());
    }

    @Test
    public void whenFindByNonExistentInvoiceId_thenReturnNull() {
        // Arrange
        String nonExistentInvoiceId = "nonExistentId";

        // Act
        Invoices found = invoiceRepository.findInvoicesByInvoiceIdentifier_InvoiceId(nonExistentInvoiceId);

        // Assert
        assertNull(found);
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