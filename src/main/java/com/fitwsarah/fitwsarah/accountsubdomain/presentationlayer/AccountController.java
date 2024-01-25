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
    public List<AccountResponseModel> getAllAccounts(@RequestParam(required = false) String accountid, @RequestParam(required = false) String username, @RequestParam(required = false) String email, @RequestParam(required = false) String city){
        return accountService.getAllAccounts(accountid, username, email, city);
    }

    @GetMapping("/{accountId}")
    public AccountResponseModel getAccountByAccountId(@PathVariable String accountId){
        return accountService.getAccountByAccountId(accountId);
    }

    @GetMapping("/users/{userId}")
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
<<<<<<< HEAD

    @GetMapping("/{accountId}/invoices")
    public List<InvoiceResponseModel> getAllInvoicesByAccountId(@PathVariable String accountId){
        return accountService.getAllInvoicesByAccountId(accountId);
=======
    @GetMapping("/users/{userId}/invoices")
    public  List<InvoiceResponseModel> getAllInvoicesByUserId(@PathVariable String userId){
        return accountService.getAllInvoicesByUserId(userId);
>>>>>>> origin/feat-56_View_all_invoices_by_user_id
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccountById(@PathVariable String accountId){

    }

    @PutMapping("/users/{userId}")
    public  ResponseEntity<AccountResponseModel> updateAccountByUserId(@RequestBody AccountRequestModel accountRequestModel, @PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(accountRequestModel, userId));
    }

    @DeleteMapping()
    public void deleteAllAccounts(){

    }
}
