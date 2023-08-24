package org.mercury.TicketService.service;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.bean.TicketLog;
import org.mercury.TicketService.dao.TicketDao;
import org.mercury.TicketService.dao.TicketLogDao;
import org.mercury.TicketService.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName TicketLogService
 * @Description TODO
 * @Author katefu
 * @Date 8/23/23 10:56 AM
 * @Version 1.0
 **/

@Service
public class TicketLogService {
    @Autowired
    private TicketLogDao ticketLogDao;

    @Autowired
    private TicketDao ticketDao;

    public List<TicketLog> getAllByTicketId(int ticketId){
        Optional<Ticket> ticketFromDB = ticketDao.findById(ticketId);
        return ticketFromDB.map(ticket -> ticketLogDao.findTicketLogsByTicket(ticket)).orElse(null);
    }

    public TicketLog getById(int id){
        return ticketLogDao.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TicketLog addTicketLog(Ticket ticket, int ticketLogCreator, String ticketLogContent) {
        TicketLog ticketLog = new TicketLog(ticket, ticketLogCreator, new Date(), ticketLogContent);

        Ticket ticketFromDB = ticketDao.findById(ticket.getTicketId()).get();
        ticketFromDB.setTicketLastUpdatedate(new Date());
        ticketDao.save(ticketFromDB);

        return ticketLogDao.save(ticketLog);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TicketLog editTicketLog(int ticketLogId, String newContent) {
        TicketLog logfromDB = ticketLogDao.findById(ticketLogId).orElse(null);
        if (logfromDB != null) {
            logfromDB.setTicketLogContent(newContent);

            Ticket ticketFromDB = ticketDao.findById(logfromDB.getTicket().getTicketId()).get();
            ticketFromDB.setTicketLastUpdatedate(new Date());
            ticketDao.save(ticketFromDB);

            return ticketLogDao.save(logfromDB);
        }
        return null;
    }

//    public Response editTicketLog(TicketLog ticketLog) {
//        try{
//            // can only edit content
//            TicketLog logFromDB = ticketLogDao.findById(ticketLog.getTicketLogId()).get();
//            logFromDB.setTicketLogContent(ticketLog.getTicketLogContent());
//            ticketLogDao.save(logFromDB);
//
//            // update ticket last update date
//            Ticket ticket = ticketLog.getTicket();
//            Ticket ticketFromDB = ticketDao.findById(ticket.getTicketId()).get();
//            ticketFromDB.setTicketLastUpdatedate(new Date());
//            ticketDao.save(ticketFromDB);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return new Response(false);
//        }
//        return new Response(true);
//    }
}
