package org.mercury.TicketService.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.bean.TicketAssign;
import org.mercury.TicketService.bean.TicketLog;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TicketWithFilesReturn
 * @Description TODO
 * @Author katefu
 * @Date 1/3/24 10:09 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketWithFilesReturn {
    private int ticketId;
    private int ticketCreator;
    private Date ticketCreationdate;
    private Date ticketLastUpdatedate;
    private String ticketTitle;
    private String ticketDescription;
    private String ticketStatus;
    private Integer ticketPriority;
    private Integer ticketFromTeam;
    private Date ticketDuedate;
    private List<TicketAssign> assigns;
    private List<TicketLog> ticketLogs;
    private List<String> files;
}
