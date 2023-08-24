package org.mercury.TicketService.requestObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TicketLogCreateRequest
 * @Description TODO
 * @Author katefu
 * @Date 8/24/23 10:27 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketLogCreateRequest {
    private int ticketId;
    private int ticketLogCreator;
    private String ticketLogContent;
}
