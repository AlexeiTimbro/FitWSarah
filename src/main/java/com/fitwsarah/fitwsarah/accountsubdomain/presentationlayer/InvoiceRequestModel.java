package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.InvoiceStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InvoiceRequestModel {
    private String accountId;
    private InvoiceStatus status;
    private LocalDateTime date;
    private LocalDateTime dueDate;
    private String paymentType;
    private double price;
}
