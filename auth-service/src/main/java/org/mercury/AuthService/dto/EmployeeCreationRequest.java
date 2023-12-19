package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName EmployeeCreationRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/19/23 12:03 AM
 * @Version 1.0
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreationRequest {
    private String employeeFirstname;
    private String employeeLastname;
    private String employeeEmail;
    private String employeeLocationCountry;
    private String employeeLocationCity;
    private String employeePhone;
    private String department;
    private String employeeRole;
}
