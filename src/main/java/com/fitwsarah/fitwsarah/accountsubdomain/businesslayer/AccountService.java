package com.fitwsarah.fitwsarah.accountsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.InvoiceResponseModel;

import java.util.List;

public interface AccountService {

    List<AccountResponseModel> getAllAccounts(String userId, String username, String email, String city);

<<<<<<< HEAD
    List<InvoiceResponseModel> getAllInvoicesByAccountId(String accountId);
=======
    List<InvoiceResponseModel> getAllInvoicesByUserId(String userId);
>>>>>>> origin/feat-56_View_all_invoices_by_user_id
    AccountResponseModel getAccountByAccountId(String accountId);

    AccountResponseModel getByUserId(String userId);

    AccountResponseModel addAccount(AccountRequestModel accountRequestModel);

    AccountResponseModel updateAccount(AccountRequestModel accountRequestModel, String userId);

    void removeAccount(String accountId);
}
