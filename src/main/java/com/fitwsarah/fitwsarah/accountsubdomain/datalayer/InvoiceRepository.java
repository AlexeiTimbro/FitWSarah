package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface InvoiceRepository extends JpaRepository<Invoices, Integer> {

             List<Invoices> findInvoicesByUserId(String userId);
}
