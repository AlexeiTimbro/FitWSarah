package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class InvoiceIndentifier {
    private String invoiceId;

     public InvoiceIndentifier(){
         this.invoiceId = UUID.randomUUID().toString();
     }

     public InvoiceIndentifier(String invoiceId){
         this.invoiceId = invoiceId;
     }


    public String getInvoiceId(){
         return invoiceId;
     }

        public void setInvoiceId(String invoiceId){
            this.invoiceId = invoiceId;
        }
}

