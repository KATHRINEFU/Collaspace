package org.mercury.EmployeeService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName EmployeeGetClientsRequest
 * @Description TODO
 * @Author katefu
 * @Date 10/15/23 8:41 PM
 * @Version 1.0
 **/
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeGetClientsRequest implements Serializable {
    private int employeeId;
}
