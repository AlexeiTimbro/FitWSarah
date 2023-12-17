package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @GetMapping()
    public List<AccountResponseModel> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    public AccountResponseModel getAccountById(@PathVariable String accountId){
        return accountService.getAccountByAccountId(accountId);
    }
    @PostMapping()
    public ResponseEntity<AccountResponseModel> addAccount(@RequestBody AccountRequestModel accountRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addAccount(accountRequestModel));
    }

    //This is for everything invoice, same logic can be applied for coach notes
    @PostMapping("/{account_Id}")
    public InvoiceResponseModel addInvoiceByAccountId(@RequestBody InvoiceRequestModel invoiceRequestModel, @PathVariable String account_Id){
        return null;
    }
    @GetMapping("/{account_Id}/invoices")
    public InvoiceResponseModel getAllInvoicesByAccountId(@PathVariable String account_Id){
        return null;
    }
    //------
    @PutMapping("/{account_Id}")
    public AccountResponseModel updateAccount(@RequestBody AccountRequestModel accountRequestModel, @PathVariable String account_Id){
        return null;
    }

    @DeleteMapping("/{account_Id}")
    public void deleteAccountById(@PathVariable String account_Id){

    }

    @DeleteMapping()
    public void deleteAllAccounts(){

    }
}
