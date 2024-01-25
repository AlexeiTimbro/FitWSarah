package com.fitwsarah.fitwsarah.accountsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class InvoiceIdentifier {
    private String invoiceId;

<<<<<<< HEAD
    public  InvoiceIdentifier(){
=======
     InvoiceIdentifier(){
>>>>>>> origin/feat-56_View_all_invoices_by_user_id
         this.invoiceId = UUID.randomUUID().toString();
     }



    public String getInvoiceId(){
         return invoiceId;
     }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}

