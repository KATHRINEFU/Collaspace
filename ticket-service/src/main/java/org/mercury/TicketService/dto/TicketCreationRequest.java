package org.mercury.TicketService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TicketCreationRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/28/23 8:15 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketCreationRequest {
    private int ticketCreator;
    private String ticketTitle;
    private String ticketDescription;
    private int ticketPriority;
    private int ticketFromTeam;
    private Date ticketDueDate;
    private int assigneeId;
    private List<Integer> viewerIds;
    private List<Integer> supervisorIds;
    private List<String> files;
}
