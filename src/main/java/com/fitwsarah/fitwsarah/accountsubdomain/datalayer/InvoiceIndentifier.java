package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class InvoiceIndentifier {
    private String invoiceId;

     InvoiceIndentifier(){
         this.invoiceId = UUID.randomUUID().toString();
     }

     public String getInvoiceId(){
         return invoiceId;
     }
}

