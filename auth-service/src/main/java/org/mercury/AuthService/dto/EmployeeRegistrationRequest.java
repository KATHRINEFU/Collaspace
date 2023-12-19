package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName EmployeeRegistrationRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/19/23 10:16 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeRegistrationRequest {
    private String employeeFirstname;
    private String employeeLastname;
    private String employeeEmail;
    private String employeeLocationCountry;
    private String employeeLocationCity;
    private String employeePhone;
    private String department;
    private String employeeRole;
    private String employeePassword;
}
