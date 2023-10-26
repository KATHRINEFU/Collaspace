package org.mercury.TicketService.controller;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.filter.TicketFilter;
import org.mercury.TicketService.http.Response;
import org.mercury.TicketService.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TicketController
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 9:30 AM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable int id){
        return ticketService.getById(id);
    }

    @GetMapping("creator/{id}")
    public List<Ticket> getTicketsByCreatorId(@PathVariable int id){
        return ticketService.getByCreatorId(id);
    }

    @GetMapping("/employee/{id}")
    public List<Ticket> getTicketsByEmployeeId(@PathVariable int id){
        return ticketService.getByEmployeeId(id);
    }

    @GetMapping("/filter")
    public List<Ticket> getTicketsWithFilter(TicketFilter ticketFilter){
        return ticketService.getWithFilter(ticketFilter);
    }

    @PutMapping("/edit")
    public Response editTicket(@RequestBody Ticket ticket){
        return ticketService.editTicket(ticket);
    }

    @PostMapping("/create")
    public Response addTicket(@RequestBody Ticket ticket){
        return ticketService.addTicket(ticket);
    }

}
