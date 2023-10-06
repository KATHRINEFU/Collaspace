package org.mercury.EmployeeService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Ticket
 * @Description TODO
 * @Author katefu
 * @Date 8/31/23 9:57 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    private int ticketId;
    private int ticketCreator;
    private Date ticketCreationdate;
    private Date ticketLastUpdatedate;
    private String ticketTitle;
    private String ticketDescription;
    private String ticketStatus;
    private TicketAssign ticketAssigns;
}
