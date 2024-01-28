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

    private String content_EN;
    private String content_FR;

    public Invoices() {
        this.invoiceIdentifier = new InvoiceIndentifier();
    }

    public Invoices(String accountId, double amount, String content_EN, String content_FR) {
        this.invoiceIdentifier = new InvoiceIndentifier();
        this.accountId = accountId;
        this.amount = amount;
        this.content_EN = content_EN;
        this.content_FR = content_FR;
    }
}

