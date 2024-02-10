package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InvoiceResponseModel {
    private String invoiceId;
    private String accountId;
    private String userId;
    private String username;
    private InvoiceStatus status;
    private LocalDateTime date;
    private LocalDateTime dueDate;
    private String paymentType;
    private double price;
}
