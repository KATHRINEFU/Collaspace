package org.mercury.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName AccountEditRequest
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 10:50 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountEditRequest {
    private String accountType;
    private String accountCurrentStatus;
    private Integer accountCurrentResponsibleDepartmentId;
    private Integer biddingPersonnel;
    private Integer salesPersonnel;
    private Integer solutionArchitectPersonnel;
    private Integer CustomerSuccessPersonnel;
}
