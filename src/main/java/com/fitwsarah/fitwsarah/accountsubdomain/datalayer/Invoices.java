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

    private String userId;

    private double amount;

    private double content;

    Invoices() {
        this.invoiceIdentifier = new InvoiceIdentifier();
    }

    public Invoices(String userId, double amount, double content) {
        this.invoiceIdentifier = new InvoiceIdentifier();
        this.userId = userId;
        this.amount = amount;
        this.content = content;
    }
}

