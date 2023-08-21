package org.mercury.EmployeeService.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName EmployeeFilter
 * @Description TODO
 * @Author katefu
 * @Date 8/19/23 11:51 AM
 * @Version 1.0
 **/

// can be filtered by location, departmentId, startdate, role
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeFilter {
    private String employeeLocationCountry;
    private String employeeLocationCity;
    private String departmentId;
    private Date employeeStartdateStart;
    private Date employeeStartdateEnd;
    private String employeeRole;
}
