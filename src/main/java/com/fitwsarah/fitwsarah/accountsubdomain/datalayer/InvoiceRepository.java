package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface InvoiceRepository extends JpaRepository<Invoices, Integer> {

             List<Invoices> findInvoicesByUserId(String userId);

    List<Invoices> findInvoicesByUserIdStartingWith(String userId);
             List<Invoices> findInvoicesByInvoiceIdentifier_InvoiceIdStartingWith(String invoiceId);
    List<Invoices> findAllInvoicesByUsernameStartingWith(String username);
    List<Invoices> findInvoicesByStatus(InvoiceStatus status);
    List<Invoices> findInvoicesByPaymentTypeStartingWith(String paymentType);
    List<Invoices> findInvoicesByPrice(double price);
}
