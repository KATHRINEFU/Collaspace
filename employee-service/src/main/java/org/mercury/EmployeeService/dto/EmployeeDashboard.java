package org.mercury.EmployeeService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mercury.EmployeeService.bean.*;

import java.util.List;

/**
 * @ClassName EmployeeDashboard
 * @Description TODO
 * @Author katefu
 * @Date 10/3/23 10:31 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeDashboard {
    // about employee
    private String firstname;
    private String lastname;
    private String department;
    private String role;

    // team
    private List<Team> teams;

    // event
    private List<Event> eventsInvolved;

    // ticket assigned
    private List<Ticket> ticketsAssigned;

    // ticket involved
    private List<Ticket> ticketsInvolved;

    // clients in progress
    private List<Client> clients;

    // announcements
    private List<Announcement> announcements;
}
