package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName AccountGetCompanyRequest
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 3:14 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountGetCompanyRequest {
    private int companyId;
}
