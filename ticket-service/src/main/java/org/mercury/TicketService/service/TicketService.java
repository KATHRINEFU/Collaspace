package org.mercury.TicketService.service;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.bean.TicketAssign;
import org.mercury.TicketService.bean.TicketLog;
import org.mercury.TicketService.criteria.SearchCriteria;
import org.mercury.TicketService.dao.TicketAssignDao;
import org.mercury.TicketService.dao.TicketDao;
import org.mercury.TicketService.filter.TicketFilter;
import org.mercury.TicketService.http.Response;
import org.mercury.TicketService.specification.TicketSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName TicketService
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 9:48 AM
 * @Version 1.0
 **/

@Service
public class TicketService {
    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketAssignDao ticketAssignDao;

    public Ticket getById(int id){
        Optional<Ticket> optionalTicket = ticketDao.findById(id);
        return optionalTicket.orElse(null);
    }

    public List<Ticket> getByEmployeeId(int id){
        return ticketDao.findByTicketCreator(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Response addTicket(Ticket ticket){
        try{
            List<TicketAssign> assignRoles = ticket.getAssigns();
            assignRoles.forEach((ticketAssign -> {
                ticketAssign.setTicket(ticket);
                // employeeId and role are already set
            }));

            // ticketlogs is null when passed in
            // is a log for creation necessary?
            List<TicketLog> logs = new ArrayList<>();
            logs.add(new TicketLog(ticket, ticket.getTicketCreator(), new Date(), "Ticket Created"));
            ticket.setTicketLogs(logs);

            ticketDao.save(ticket);
            return new Response(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Response(false);
        }


    }

    public List<Ticket> getWithFilter(TicketFilter ticketFilter){
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        String ticketCreator = ticketFilter.getTicketCreator();
        if(!ticketCreator.isEmpty()){
            searchCriteriaList.add(new SearchCriteria("ticketCreator", ":", ticketCreator));
        }
        Date ticketCreationdateStart = ticketFilter.getTicketCreationdateStart();
        if(ticketCreationdateStart!=null){
            searchCriteriaList.add(new SearchCriteria("ticketCreationdateStart", ">", ticketCreationdateStart));
        }

        Date ticketCreationdateEnd = ticketFilter.getTicketCreationdateEnd();
        if(ticketCreationdateEnd!=null){
            searchCriteriaList.add(new SearchCriteria("ticketCreationdateEnd", "<", ticketCreationdateEnd));
        }

        Date ticketLastUpdatedateStart = ticketFilter.getTicketLastUpdatedateStart();
        if(ticketLastUpdatedateStart!=null){
            searchCriteriaList.add(new SearchCriteria("ticketLastUpdatedateStart", ">", ticketLastUpdatedateStart));
        }

        Date ticketLastUpdatedateEnd = ticketFilter.getTicketLastUpdatedateEnd();
        if(ticketLastUpdatedateEnd!=null){
            searchCriteriaList.add(new SearchCriteria("ticketLastUpdatedateEnd", ">", ticketLastUpdatedateEnd));
        }

        String ticketTitle = ticketFilter.getTicketTitle();
        if(!ticketTitle.isEmpty()){
            searchCriteriaList.add(new SearchCriteria("ticketTitle", ":", ticketTitle));
        }
        String ticketStatus = ticketFilter.getTicketTitle();
        if(!ticketStatus.isEmpty()){
            searchCriteriaList.add(new SearchCriteria("ticketStatus", ":", ticketStatus));
        }

        List<TicketSpecification> specifications = searchCriteriaList.stream()
                .map(TicketSpecification::new).toList();

        Specification<Ticket> finalSpecification = Specification.where(specifications.get(0));
        for (int i = 1; i < specifications.size(); i++) {
            finalSpecification = finalSpecification.and(specifications.get(i));
        }

        return ticketDao.findAll(finalSpecification);

    }

    public Response editTicket(Ticket ticket){
        // can only edit ticket's status, description
        try{
            Ticket ticketFromDB = ticketDao.findById(ticket.getTicketId()).get();
            ticketFromDB.setTicketDescription(ticket.getTicketDescription());
            ticketFromDB.setTicketStatus(ticket.getTicketStatus());
            ticketDao.save(ticketFromDB);
            return new Response(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new Response(false);
        }
    }
}
