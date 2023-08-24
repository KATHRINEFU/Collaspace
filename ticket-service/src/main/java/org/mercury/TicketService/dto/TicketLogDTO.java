package org.mercury.TicketService.dto;

import lombok.Getter;
import lombok.Setter;
import org.mercury.TicketService.bean.TicketLog;

import java.util.Date;

/**
 * @ClassName TicketLogDTO
 * @Description TODO
 * @Author katefu
 * @Date 8/24/23 10:50 AM
 * @Version 1.0
 **/
@Getter
@Setter
public class TicketLogDTO {
    private int ticketLogId;
    private int ticketId;
    private int ticketLogCreator;
    private Date ticketLogCreationdate;
    private String ticketLogContent;

    public TicketLogDTO(TicketLog ticketLog) {
        this.ticketLogId = ticketLog.getTicketLogId();
        this.ticketId = ticketLog.getTicket().getTicketId();
        this.ticketLogCreator = ticketLog.getTicketLogCreator();
        this.ticketLogCreationdate = ticketLog.getTicketLogCreationdate();
        this.ticketLogContent = ticketLog.getTicketLogContent();
    }
}
