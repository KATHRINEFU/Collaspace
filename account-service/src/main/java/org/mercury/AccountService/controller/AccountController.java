package org.mercury.AccountService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mercury.AccountService.bean.Account;
import org.mercury.AccountService.dto.AccountEditRequest;
import org.mercury.AccountService.dto.AccountRequest;
import org.mercury.AccountService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AccountController
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 7:00 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/account")
@Tag(name = "AccountService", description = "account service")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Operation(summary = "get all accounts", description = "get all accounts", tags = { "AccountService" })
    @GetMapping("/all")
    public List<Account> getAllAccounts(){
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public  Account getAccountById(@PathVariable int id){
        return accountService.getAccountById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> addAccount(@RequestBody AccountRequest accountRequest){
        try {
            Account addedAccount = accountService.addAccount(accountRequest);
            if (addedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Company created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create company");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editAccount(@PathVariable int id, @RequestBody AccountEditRequest accountEditRequest) {
        try {
            Account editedAccount = accountService.editAccount(id, accountEditRequest);
            if (editedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Account edited successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to edit account");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("push/{id}")
    public ResponseEntity<String> pushAccount(@PathVariable int id, @RequestBody String currentStatus) {
        try {
            Account pushedAccount = accountService.pushAccount(id, currentStatus);
            if (pushedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Account pushed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to push account");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
