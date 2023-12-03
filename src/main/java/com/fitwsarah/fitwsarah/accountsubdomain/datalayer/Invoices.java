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

    @Embedded
    private AccountIdentifier accountIdentifier;

    private double amount;

    private double content;

    Invoices() {
        this.invoiceIdentifier = new InvoiceIndentifier();
    }

    public Invoices(AccountIdentifier accountIdentifier, double amount, double content) {
        this.invoiceIdentifier = new InvoiceIndentifier();
        this.accountIdentifier = accountIdentifier;
        this.amount = amount;
        this.content = content;
    }
}

