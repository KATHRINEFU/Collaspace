package org.mercury.TicketService.service;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.criteria.SearchCriteria;
import org.mercury.TicketService.dao.TicketDao;
import org.mercury.TicketService.filter.TicketFilter;
import org.mercury.TicketService.http.Response;
import org.mercury.TicketService.specification.TicketSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Ticket getById(int id){
        Optional<Ticket> optionalTicket = ticketDao.findById(id);
        return optionalTicket.orElse(null);
    }

    public List<Ticket> getByEmployeeId(int id){
        return ticketDao.findByTicketCreator(id);
    }
    public Response addTicket(Ticket ticket){
        ticketDao.save(ticket);
        return new Response(true);
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