package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InvoicesTest {

    @Test
    void invoicesConstructorTest() {
        String accountId = "accountId";
        String userId = "userId";
        String username = "username";
        InvoiceStatus status = InvoiceStatus.PAID;
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        String paymentType = "credit";
        double price = 100.00;

        Invoices invoices = new Invoices(accountId, userId, username, status, date, dueDate, paymentType, price);

        assertEquals(accountId, invoices.getAccountId());
        assertEquals(userId, invoices.getUserId());
        assertEquals(username, invoices.getUsername());
        assertEquals(status, invoices.getStatus());
        assertEquals(date, invoices.getDate());
        assertEquals(dueDate, invoices.getDueDate());
        assertEquals(paymentType, invoices.getPaymentType());
        assertEquals(price, invoices.getPrice(), 0.01);
    }

}