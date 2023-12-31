package org.mercury.AccountService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import org.mercury.AccountService.bean.Account;
import org.mercury.AccountService.dto.*;
import org.mercury.AccountService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public  AccountWithFilesReturn getAccountById(@PathVariable int id){
        return accountService.getAccountById(id);
    }

    @GetMapping("/bydepartment/{id}")
    public List<AccountWithFilesReturn> getAccountsByDepartmentId(@PathVariable int id){
        return accountService.getByDepartmentId(id);
    }

    @GetMapping("/withcompany/{id}")
    public CompletableFuture<ResponseEntity<?>> getAccountWithCompanyById(@PathVariable int id) {
        return accountService.sendRequestForAccountWithCompanyData(id)
                .thenApply(accountWithCompanyReturn -> {
                    if (accountWithCompanyReturn == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
                    }
                    return ResponseEntity.ok(accountWithCompanyReturn);
                });
    }

    @PostMapping("/create")
    public ResponseEntity<String> addAccount(@RequestBody AccountCreateRequest request){
        try {
            Account addedAccount = accountService.addAccount(request);
            if (addedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create account");
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

    @PutMapping("/push/{id}")
    public ResponseEntity<String> pushAccount(@PathVariable int id, @RequestBody AccountPushRequest request) {
        try {
            Account pushedAccount = accountService.pushAccount(id, request);
            if (pushedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Account pushed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to push account");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable int id, @RequestBody AccountUpdateStatusRequest request) {
        try {
            Account updatedAccount = accountService.updateAccountStatus(id, request);
            if (updatedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Account status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update account status");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/adddocuments/{id}")
    public ResponseEntity<String> addAccountDocuments(@PathVariable int id, @RequestBody AccountAddDocumentsRequest request) {
        try {
            Account updatedAccount = accountService.addAccountDocuments(id, request);
            if (updatedAccount != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Account documents added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add account documents");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
