package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
    private String username;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    private LocalDateTime date;
    private LocalDateTime dueDate;
    private String paymentType;
    private double price;

    public Invoices() {
        this.invoiceIdentifier = new InvoiceIndentifier();
    }

    public Invoices(String accountId, String username, InvoiceStatus status, LocalDateTime date, LocalDateTime dueDate, String paymentType, double price) {
        this.invoiceIdentifier = new InvoiceIndentifier();
        this.accountId = accountId;
        this.username = username;
        this.status = status;
        this.date = date;
        this.dueDate = dueDate;
        this.paymentType = paymentType;
        this.price = price;
    }
}

