package org.mercury.AccountService.service;

import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.mercury.AccountService.bean.Account;
import org.mercury.AccountService.bean.Company;
import org.mercury.AccountService.dao.AccountDao;
import org.mercury.AccountService.dto.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName AccountService
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 7:00 PM
 * @Version 1.0
 **/

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final WebClient webClient;

    private CompletableFuture<Company> companyFuture;

    public AccountService(){
        this.webClient = WebClient.create("http://localhost:8080/document");
    }

    public List<Account> getAll() {
        return accountDao.findAll();
    }

    public AccountWithFilesReturn getAccountById(int id) {
        Account account =  accountDao.findById(id).orElse(null);
        if(account==null) return null;
        AccountWithFilesReturn accountWithFiles = new AccountWithFilesReturn();
        accountWithFiles.setAccountId(account.getAccountId());
        accountWithFiles.setAccountType(account.getAccountType());
        accountWithFiles.setCompanyId(account.getCompanyId());
        accountWithFiles.setAccountCurrentStatus(account.getAccountCurrentStatus());
        accountWithFiles.setAccountCurrentResponsibleDepartmentId(account.getAccountCurrentResponsibleDepartmentId());
        accountWithFiles.setBiddingPersonnel(account.getBiddingPersonnel());
        accountWithFiles.setSalesPersonnel(account.getSalesPersonnel());
        accountWithFiles.setCustomerSuccessPersonnel(account.getCustomerSuccessPersonnel());
        accountWithFiles.setSolutionArchitectPersonnel(account.getSolutionArchitectPersonnel());
        accountWithFiles.setAccountCreationdate(account.getAccountCreationdate());
        accountWithFiles.setAccountLastUpdatedate(account.getAccountLastUpdatedate());

        ResponseEntity<List<String>> response = webClient.get()
                .uri("/byaccount/{id}",account.getAccountId())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {})
                .block(); // This makes the call synchronous
        if(response!=null){
            accountWithFiles.setFiles(response.getBody());
        }

        return accountWithFiles;
    }

    @RabbitListener(queues = {"q.return-account-company"})
    public void onListenReturnAccountCompany(Company company){
        log.info("Return-account-company message received: {}", company.getCompanyName());
        companyFuture.complete(company);
    }

    public CompletableFuture<AccountWithCompanyReturn> sendRequestForAccountWithCompanyData(int id){
        // TODO: return account and company info

        Account account = accountDao.findById(id).orElse(null);
        if (account == null) {
            CompletableFuture<AccountWithCompanyReturn> future = new CompletableFuture<>();
            future.completeExceptionally(new NotFoundException("Account not found"));
            return future;
        }

        AccountWithCompanyReturn accountWithCompanyReturn = new AccountWithCompanyReturn();
        accountWithCompanyReturn.setAccount(account);

        // send request to company service
        companyFuture = new CompletableFuture<>();
        CompletableFuture<AccountWithCompanyReturn> accountCompanyFuture = new CompletableFuture<>();
        rabbitTemplate.convertAndSend("", "q.get-account-company", account.getCompanyId());

        companyFuture.thenApply((company -> {
            accountWithCompanyReturn.setCompany(company);
            log.info("Returning account with company: {}, {}", accountWithCompanyReturn.getAccount().getAccountId(), accountWithCompanyReturn.getCompany().getCompanyId());
            accountCompanyFuture.complete(accountWithCompanyReturn);
            return accountCompanyFuture;
        }));
        return accountCompanyFuture;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Account addAccount(AccountRequest accountRequest) {
        Account newAccount = new Account();
        newAccount.setAccountType(accountRequest.getAccountType());
        newAccount.setCompanyId(accountRequest.getCompanyId());
        newAccount.setAccountCurrentStatus("potential");
        newAccount.setAccountCurrentResponsibleDepartmentId(1);
        newAccount.setBiddingPersonnel(accountRequest.getBiddingPersonnel());
        newAccount.setSalesPersonnel(accountRequest.getSalesPersonnel());
        newAccount.setSolutionArchitectPersonnel(accountRequest.getSolutionArchitectPersonnel());
        newAccount.setCustomerSuccessPersonnel(accountRequest.getCustomerSuccessPersonnel());
        newAccount.setAccountCreationdate(new Date());
        newAccount.setAccountLastUpdatedate(new Date());

        return accountDao.save(newAccount);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Account editAccount(int id, AccountEditRequest accountEditRequest) {
        Optional<Account> optionalAccount = accountDao.findById(id);
        if(optionalAccount.isEmpty()) return null;
        Account accountFromDB = optionalAccount.get();

        if(!accountEditRequest.getAccountCurrentStatus().isEmpty()){
            accountFromDB.setAccountCurrentStatus(accountEditRequest.getAccountCurrentStatus());
        }
        if(accountEditRequest.getAccountCurrentResponsibleDepartmentId()!=null){
            accountFromDB.setAccountCurrentResponsibleDepartmentId(accountEditRequest.getAccountCurrentResponsibleDepartmentId());
        }
        if(accountEditRequest.getBiddingPersonnel()!=null){
            accountFromDB.setBiddingPersonnel(accountEditRequest.getBiddingPersonnel());
        }
        if(accountEditRequest.getSalesPersonnel()!=null){
            accountFromDB.setSalesPersonnel(accountEditRequest.getSalesPersonnel());
        }
        if(accountEditRequest.getSolutionArchitectPersonnel()!=null){
            accountFromDB.setSolutionArchitectPersonnel(accountEditRequest.getSolutionArchitectPersonnel());
        }
        if(accountEditRequest.getCustomerSuccessPersonnel()!=null){
            accountFromDB.setCustomerSuccessPersonnel(accountEditRequest.getCustomerSuccessPersonnel());
        }

        accountFromDB.setAccountLastUpdatedate(new Date());
        return accountDao.save(accountFromDB);
    }

    public Account pushAccount(int id, AccountPushRequest request) {
        Account account = accountDao.findById(id).orElse(null);
        if(account==null) return null;

        account.setAccountCurrentResponsibleDepartmentId(request.getCurDepartmentId() + 1);
        account.setAccountLastUpdatedate(new Date());

        return accountDao.save(account);
    }

    public List<AccountWithFilesReturn> getByDepartmentId(int departmentId) {
        List<Account> accounts =  accountDao.findByAccountCurrentResponsibleDepartmentId(departmentId);
        List<AccountWithFilesReturn> accountsWithFiles = new ArrayList<>();
        accounts.forEach(account -> {
            accountsWithFiles.add(getAccountById(account.getAccountId()));
        });

        return accountsWithFiles;
    }

    public Account updateAccountStatus(int id, AccountUpdateStatusRequest request) {
        Account account = accountDao.findById(id).orElse(null);
        if(account==null) return null;
        account.setAccountCurrentStatus(request.getStatus());
        return accountDao.save(account);
    }

    public Account addAccountDocuments(int id, AccountAddDocumentsRequest request) {
        Account account = accountDao.findById(id).orElse(null);
        if(account==null) return null;

        if(request.getFiles()!=null && !request.getFiles().isEmpty()){
            List<DocumentCreationRequest> documentRequests = new ArrayList<>();
            request.getFiles().forEach(file -> {
                DocumentCreationRequest documentRequest = new DocumentCreationRequest(file, "account", account.getAccountId());
                documentRequests.add(documentRequest);
            });
            rabbitTemplate.convertAndSend("", "q.create-document", documentRequests);
        }

        return account;
    }
}
