package org.mercury.TicketService.bean;

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
 * @Date 8/22/23 9:21 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "TICKET")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id
    @SequenceGenerator(name = "ticket_seq_gen", sequenceName = "TICKET_TICKET_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="ticket_seq_gen", strategy = GenerationType.AUTO)
    private int ticketId;
    @Column
    private int ticketCreator;
    @Column
    private Date ticketCreationdate;
    @Column
    private Date ticketLastUpdatedate;
    @Column
    private String ticketTitle;
    @Column
    private String ticketDescription;
    @Column
    private String ticketStatus;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "ticket")
    private List<TicketAssign> assigns;
}
