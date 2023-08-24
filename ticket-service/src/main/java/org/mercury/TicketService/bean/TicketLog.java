package org.mercury.TicketService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TicketLog
 * @Description TODO
 * @Author katefu
 * @Date 8/23/23 10:39 AM
 * @Version 1.0
 **/

@Entity
@Table(name = "TICKET_LOG")
@Setter @NoArgsConstructor @AllArgsConstructor
public class TicketLog {
    @Id
    @SequenceGenerator(name = "ticket_log_seq_gen", sequenceName = "TICKET_LOG_TICKET_LOG_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="ticket_log_seq_gen", strategy = GenerationType.AUTO)
    private int ticketLogId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Ticket ticket;

    @Column
    private int ticketLogCreator;

    @Column
    private Date ticketLogCreationdate;

    @Column
    private String ticketLogContent;

    public TicketLog(Ticket ticket, int ticketLogCreator, Date ticketLogCreationdate, String ticketLogContent) {
        this.ticket = ticket;
        this.ticketLogCreator = ticketLogCreator;
        this.ticketLogCreationdate = ticketLogCreationdate;
        this.ticketLogContent = ticketLogContent;
    }

    public int getTicketLogId() {
        return ticketLogId;
    }

    @JsonIgnore
    public Ticket getTicket() {
        return ticket;
    }

    public int getTicketLogCreator() {
        return ticketLogCreator;
    }

    public Date getTicketLogCreationdate() {
        return ticketLogCreationdate;
    }

    public String getTicketLogContent() {
        return ticketLogContent;
    }
}
