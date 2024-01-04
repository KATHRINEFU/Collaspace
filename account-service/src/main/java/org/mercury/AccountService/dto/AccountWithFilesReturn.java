package org.mercury.AccountService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AccountWithFilesReturn
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 8:39 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountWithFilesReturn {
    private int accountId;
    private String accountType;
    private int companyId;
    private String accountCurrentStatus;
    private int accountCurrentResponsibleDepartmentId;
    private int biddingPersonnel;
    private int salesPersonnel;
    private int solutionArchitectPersonnel;
    private int customerSuccessPersonnel;
    private Date accountCreationdate;
    private Date accountLastUpdatedate;
    private List<String> files;
}
