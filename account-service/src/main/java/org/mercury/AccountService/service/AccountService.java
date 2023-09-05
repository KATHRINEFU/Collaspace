package org.mercury.AccountService.service;

import org.mercury.AccountService.bean.Account;
import org.mercury.AccountService.dao.AccountDao;
import org.mercury.AccountService.dto.AccountEditRequest;
import org.mercury.AccountService.dto.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName AccountService
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 7:00 PM
 * @Version 1.0
 **/

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;


    public List<Account> getAll() {
        return accountDao.findAll();
    }

    public Account getAccountById(int id) {
        return accountDao.findById(id).orElse(null);
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

    public Account pushAccount(int id, String currentStatus) {
        Optional<Account> optionalAccount = accountDao.findById(id);
        if(optionalAccount.isEmpty()) return null;
        Account accountFromDB = optionalAccount.get();

        accountFromDB.setAccountCurrentResponsibleDepartmentId(accountFromDB.getAccountCurrentResponsibleDepartmentId() + 1);
        if(!currentStatus.isEmpty()){
            accountFromDB.setAccountCurrentStatus(currentStatus);
        }
        accountFromDB.setAccountLastUpdatedate(new Date());

        return accountDao.save(accountFromDB);
    }
}
