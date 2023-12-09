package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRequestModel {
    private String accountId;
    private double amount;

    private double content;
}
