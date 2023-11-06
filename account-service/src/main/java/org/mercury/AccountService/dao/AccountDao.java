package org.mercury.AccountService.dao;

import org.mercury.AccountService.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDao extends JpaRepository<Account, Integer> {
    List<Account> findByAccountCurrentResponsibleDepartmentId(int departmentId);
}
