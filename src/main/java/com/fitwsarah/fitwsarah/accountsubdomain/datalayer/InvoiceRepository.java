package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Embeddable
public interface InvoiceRepository extends JpaRepository<Invoices, Integer> {
<<<<<<< HEAD
    List<Invoices> findAllInvoicesByAccountId(String accountId);

=======
    List<Invoices> findInvoicesByUserId(String userId);
>>>>>>> origin/feat-56_View_all_invoices_by_user_id
}
