package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="invoices")
@Data
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private InvoiceIdentifier invoiceIdentifier;

<<<<<<< HEAD
     private String accountId;
=======
    private String userId;
>>>>>>> origin/feat-56_View_all_invoices_by_user_id

    private double amount;

    private String content;

    Invoices() {
<<<<<<< HEAD
    }
    public Invoices( String accountId, double amount, String content) {
        this.invoiceIdentifier = new InvoiceIdentifier();
        this.accountId = accountId;
=======
        this.invoiceIdentifier = new InvoiceIdentifier();
    }

    public Invoices(String userId, double amount, double content) {
        this.invoiceIdentifier = new InvoiceIdentifier();
        this.userId = userId;
>>>>>>> origin/feat-56_View_all_invoices_by_user_id
        this.amount = amount;
        this.content = content;
    }
}

