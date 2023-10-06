package org.mercury.TeamService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName EmployeeGetTeamsRequest
 * @Description TODO
 * @Author katefu
 * @Date 10/4/23 11:20 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeGetTeamsRequest implements Serializable {
    private int employeeId;
}
