package org.mercury.TicketService.dao;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.bean.TicketLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketLogDao extends JpaRepository<TicketLog, Integer> {
    public List<TicketLog> findTicketLogsByTicket(Ticket ticket);
}
