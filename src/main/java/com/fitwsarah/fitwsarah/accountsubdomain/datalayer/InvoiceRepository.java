package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public interface InvoiceRepository extends JpaRepository<Invoices, Integer> {
}
