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
    public List<AccountResponseModel> getAllAccounts(@RequestParam(required = false) String accountId, @RequestParam(required = false) String username, @RequestParam(required = false) String email, @RequestParam(required = false) String city){
        return accountService.getAllAccounts(accountId, username, email, city);
    }

    @GetMapping("/{accountId}")
    public AccountResponseModel getAccountById(@PathVariable String accountId){
        return accountService.getAccountByAccountId(accountId);
    }

    @GetMapping("/{userId}")
    public AccountResponseModel getAccountByUserId(@PathVariable String userId){
        return accountService.getByUserId(userId);
    }


    @PostMapping()
    public ResponseEntity<AccountResponseModel> addAccount(@RequestBody AccountRequestModel accountRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addAccount(accountRequestModel));
    }

    @PostMapping("/{accountId}")
    public InvoiceResponseModel addInvoiceByAccountId(@RequestBody InvoiceRequestModel invoiceRequestModel, @PathVariable String accountId){
        return null;
    }
    @GetMapping("/{accountId}/invoices")
    public InvoiceResponseModel getAllInvoicesByAccountId(@PathVariable String accountId){
        return null;
    }
    //------


    @DeleteMapping("/{accountId}")
    public void deleteAccountById(@PathVariable String accountId){

    }

    @DeleteMapping()
    public void deleteAllAccounts(){

    }
}
