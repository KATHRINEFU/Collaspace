package org.mercury.TicketService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TicketEditRequest
 * @Description TODO
 * @Author katefu
 * @Date 1/2/24 5:55 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketEditRequest {
    private int ticketId;
    private String ticketDescription;
    private String ticketStatus;
    private List<Integer> addViewerIds;
    private List<Integer> addSupervisorIds;
}
