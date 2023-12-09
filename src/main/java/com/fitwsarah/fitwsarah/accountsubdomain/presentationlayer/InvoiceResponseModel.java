package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponseModel {
    private String invoiceId;
    private String accountId;
    private double amount;
    private String content;
}
