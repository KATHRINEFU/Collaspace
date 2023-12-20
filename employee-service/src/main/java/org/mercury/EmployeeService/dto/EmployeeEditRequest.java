package org.mercury.EmployeeService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName EmployeeEditRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/20/23 3:14 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeEditRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String locationCity;
    private String locationCountry;
    private String phone;
    private String role;
    private Date startdate;
}
