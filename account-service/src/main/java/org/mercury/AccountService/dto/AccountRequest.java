package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName AccountRequest
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 7:42 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountRequest {
    private String accountType;
    private int companyId;
    private int biddingPersonnel;
    private int salesPersonnel;
    private int solutionArchitectPersonnel;
    private int CustomerSuccessPersonnel;
}
