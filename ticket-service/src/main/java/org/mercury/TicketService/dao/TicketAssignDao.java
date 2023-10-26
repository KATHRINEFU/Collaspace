package org.mercury.TicketService.dao;

import org.mercury.TicketService.bean.TicketAssign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName TicketAssignDao
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 9:14 PM
 * @Version 1.0
 **/
public interface TicketAssignDao extends JpaRepository<TicketAssign, Integer> {
    List<TicketAssign> findByEmployeeId(int id);

}
