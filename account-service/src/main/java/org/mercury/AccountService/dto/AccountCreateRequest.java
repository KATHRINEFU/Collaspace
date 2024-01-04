package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName AccountCreateRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 11:48 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountCreateRequest {
    private int companyId;
    private String accountStatus;
    private String accountType;
    private int accountCreator;
    private int biddingPersonnel;
    private int salesPersonnel;
    private int solutionArchitectPersonnel;
    private int customerSuccessPersonnel;
}
