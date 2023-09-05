package org.mercury.AccountService.dao;

import org.mercury.AccountService.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Integer> {
}
