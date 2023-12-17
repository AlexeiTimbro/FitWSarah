package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceResponseModel {
    private String invoiceId;
    private String accountId;
    private double amount;

    private double content;
}
