package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mercury.AccountService.bean.Account;
import org.mercury.AccountService.bean.Company;

/**
 * @ClassName AccountWithCompanyReturn
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 2:50 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountWithCompanyReturn {
    private Account account;
    private Company company;
}
