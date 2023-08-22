package org.mercury.TicketService.dao;

import org.mercury.TicketService.bean.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TicketDao extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {
    public List<Ticket> findByTicketCreator(int employeeId);
}
