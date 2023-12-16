package org.mercury.TicketService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Employee
 * @Description TODO
 * @Author katefu
 * @Date 11/11/23 6:31 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Employee {
    private int employeeId;
    private String employeeFirstname;
    private String employeeLastname;
    private String employeeEmail;
    private String employeeLocationCountry;
    private String employeeLocationCity;
    private String employeePhone;
    private Integer departmentId;
    private Date employeeBirthday;
    private Date employeeStartdate;
    private String employeeRole;
    private Integer employeeManager;
    private String employeeProfileUrl;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeFirstname='" + employeeFirstname + '\'' +
                ", employeeLastname='" + employeeLastname + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeeLocationCountry='" + employeeLocationCountry + '\'' +
                ", employeeLocationCity='" + employeeLocationCity + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", departmentId=" + departmentId +
                ", employeeBirthday=" + employeeBirthday +
                ", employeeStartdate=" + employeeStartdate +
                ", employeeRole='" + employeeRole + '\'' +
                ", employeeManager=" + employeeManager +
                ", employeeProfileUrl='" + employeeProfileUrl + '\'' +
                '}';
    }
}
