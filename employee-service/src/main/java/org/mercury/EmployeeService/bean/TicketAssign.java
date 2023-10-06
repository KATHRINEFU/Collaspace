package org.mercury.EmployeeService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TicketAssign
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 11:38 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketAssign {
    private int ticketAssignId;
    private int employeeId;
    private int teamId;
    private String role;
}
