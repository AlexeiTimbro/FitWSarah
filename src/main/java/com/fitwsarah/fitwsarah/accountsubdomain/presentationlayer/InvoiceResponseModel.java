package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InvoiceResponseModel {
 String invoiceId;
<<<<<<< HEAD
 String accountId;
   double amount;
   String content;
=======
   String userId;
   double amount;

   double content;
>>>>>>> origin/feat-56_View_all_invoices_by_user_id
}
