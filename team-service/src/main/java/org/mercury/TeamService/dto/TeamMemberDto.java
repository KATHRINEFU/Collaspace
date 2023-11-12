package org.mercury.TeamService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TeamMemberDto
 * @Description TODO
 * @Author katefu
 * @Date 11/11/23 6:57 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeamMemberDto {
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

    private Date joindate;
    private String role;

    @Override
    public String toString() {
        return "TeamMemberDto{" +
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
                ", joindate=" + joindate +
                ", role='" + role + '\'' +
                '}';
    }
}
