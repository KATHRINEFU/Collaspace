package org.mercury.EmployeeService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Client
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 10:41 AM
 * @Version 1.0
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private int accountId;
    private String accountType;
    private int companyId;
    private String companyName;
    private String accountCurrentStatus;
    private int accountCurrentResponsibleDepartmentId;
    private int biddingPersonnel;
    private int salesPersonnel;
    private int solutionArchitectPersonnel;
    private int CustomerSuccessPersonnel;
    private Date accountCreationdate;
    private Date accountLastUpdatedate;
}
