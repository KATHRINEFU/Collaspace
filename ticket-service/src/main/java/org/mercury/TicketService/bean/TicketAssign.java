package org.mercury.TicketService.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TicketAssign
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 6:02 PM
 * @Version 1.0
 **/

@Entity
@Table(name = "TICKET_ASSIGN")
public class TicketAssign {
    @Id
    @SequenceGenerator(name = "ticket_assign_seq_gen", sequenceName = "TICKET_ASSIGN_TICKET_ASSIGN_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="ticket_assign_seq_gen", strategy = GenerationType.AUTO)
    private int ticketAssignId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Ticket ticket;

    @Column
    private Integer employeeId;

    @Column
    private Integer teamId;

    @Column
    private String role;
    // assignee, supervisor, viewer

    @Column
    private Date ticketAssigndate;


    public TicketAssign() {
    }

    public TicketAssign(int ticketAssignId, Ticket ticket, Integer employeeId, Integer teamId, String role, Date ticketAssigndate) {
        this.ticketAssignId = ticketAssignId;
        this.ticket = ticket;
        this.employeeId = employeeId;
        this.teamId = teamId;
        this.role = role;
        this.ticketAssigndate = ticketAssigndate;
    }

    public int getTicketAssignId() {
        return ticketAssignId;
    }

    public void setTicketAssignId(int ticketAssignId) {
        this.ticketAssignId = ticketAssignId;
    }

    @JsonIgnore
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getTicketAssigndate() {
        return ticketAssigndate;
    }

    public void setTicketAssigndate(Date ticketAssigndate) {
        this.ticketAssigndate = ticketAssigndate;
    }
}
