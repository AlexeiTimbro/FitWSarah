package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;


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
    private InvoiceIndentifier invoiceIdentifier;

    private String accountId;

    private double amount;

    private String content;

    Invoices() {
        this.invoiceIdentifier = new InvoiceIndentifier();
    }

    public Invoices(String accountId, double amount, String content) {
        this.invoiceIdentifier = new InvoiceIndentifier();
        this.accountId = accountId;
        this.amount = amount;
        this.content = content;
    }
}

