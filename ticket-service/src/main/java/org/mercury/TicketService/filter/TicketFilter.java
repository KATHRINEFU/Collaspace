package org.mercury.TicketService.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName TicketFilter
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 10:32 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketFilter {
    // filter based on creator, creation date, title, status
    private String ticketCreator;
    private Date ticketCreationdateStart;
    private Date ticketCreationdateEnd;
    private Date ticketLastUpdatedateStart;
    private Date ticketLastUpdatedateEnd;
    private String ticketTitle;
    private String ticketStatus;
}
