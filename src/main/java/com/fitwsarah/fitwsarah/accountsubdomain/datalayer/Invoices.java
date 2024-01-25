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

     private String accountId;

    private double amount;

    private String content;

    Invoices() {
    }
    public Invoices( String accountId, double amount, String content) {
        this.invoiceIdentifier = new InvoiceIdentifier();
        this.accountId = accountId;
        this.amount = amount;
        this.content = content;
    }
}

