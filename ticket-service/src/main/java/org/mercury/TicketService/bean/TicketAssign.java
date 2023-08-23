package org.mercury.TicketService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TicketAssign
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 6:02 PM
 * @Version 1.0
 **/

@Entity
@Table(name = "TICKET_ASSIGN")

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketAssign {
    @Id
    @SequenceGenerator(name = "ticket_assign_seq_gen", sequenceName = "TICKET_ASSIGN_TICKET_ASSIGN_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="ticket_assign_seq_gen", strategy = GenerationType.AUTO)
    private int ticketAssignId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Ticket ticket;

    @Column
    private int employeeId;

    @Column
    private String role;
}
