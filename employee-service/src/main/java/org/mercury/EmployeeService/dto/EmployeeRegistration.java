package org.mercury.EmployeeService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName EmployeeRegistration
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 4:05 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeRegistration {
    private String employeeFirstname;
    private String employeeLastname;
    private String employeeEmail;
    private String employeeLocationCountry;
    private String employeeLocationCity;
    private String employeePhone;
    private String department;
    private String employeeRole;
    private String password;
}
