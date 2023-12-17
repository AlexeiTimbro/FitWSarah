package com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.businesslayer.AccountService;
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
        return null;
    }

    @GetMapping("/{accountId}")
    public AccountResponseModel getAccountById(@PathVariable String accountId){
        return accountService.getAccountByAccountId(accountId);
    }

    @PostMapping()
    public AccountResponseModel addAccount(@RequestBody AccountRequestModel accountRequestModel){
        return null;
    }

    @PutMapping("/{accountId}")
    public AccountResponseModel updateAccount(@RequestBody AccountRequestModel accountRequestModel, @PathVariable String accountId){
        return null;
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccountById(@PathVariable String accountId){

    }

    @DeleteMapping()
    public void deleteAllAccounts(){

    }
}
