package org.mercury.TeamService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName TeamAccountDto
 * @Description TODO
 * @Author katefu
 * @Date 11/17/23 10:53 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Account {
    private int accountId;
    private String accountType;
    private int companyId;
    private String accountCurrentStatus;
    private int accountCurrentResponsibleDepartmentId;
    private int biddingPersonnel;
    private int salesPersonnel;
    private int solutionArchitectPersonnel;
    private int CustomerSuccessPersonnel;
    private Date accountCreationdate;
    private Date accountLastUpdatedate;

    private List<String> files;

    @Override
    public String toString() {
        return "TeamAccountDto{" +
                "accountId=" + accountId +
                ", accountType='" + accountType + '\'' +
                ", companyId=" + companyId +
                ", accountCurrentStatus='" + accountCurrentStatus + '\'' +
                ", accountCurrentResponsibleDepartmentId=" + accountCurrentResponsibleDepartmentId +
                ", biddingPersonnel=" + biddingPersonnel +
                ", salesPersonnel=" + salesPersonnel +
                ", solutionArchitectPersonnel=" + solutionArchitectPersonnel +
                ", CustomerSuccessPersonnel=" + CustomerSuccessPersonnel +
                ", accountCreationdate=" + accountCreationdate +
                ", accountLastUpdatedate=" + accountLastUpdatedate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
