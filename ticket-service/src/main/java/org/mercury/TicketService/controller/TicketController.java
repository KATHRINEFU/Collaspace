package org.mercury.TicketService.controller;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.dto.TicketCreationRequest;
import org.mercury.TicketService.dto.TicketEditRequest;
import org.mercury.TicketService.dto.TicketWithFilesReturn;
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
    public TicketWithFilesReturn getTicketById(@PathVariable int id){
        return ticketService.getTicketWithFilesById(id);
    }

    @GetMapping("/creator/{id}")
    public List<Ticket> getTicketsByCreatorId(@PathVariable int id){
        return ticketService.getByCreatorId(id);
    }

    @GetMapping("/employee/{id}")
    public List<TicketWithFilesReturn> getTicketsByEmployeeId(@PathVariable int id){
        return ticketService.getByEmployeeId(id);
    }

    @GetMapping("/team/{id}")
    public List<Ticket> getTicketsByTeamId(@PathVariable int id){
        return ticketService.getByTeamId(id);
    }

    @GetMapping("/filter")
    public List<Ticket> getTicketsWithFilter(TicketFilter ticketFilter){
        return ticketService.getWithFilter(ticketFilter);
    }

    @PutMapping("/edit")
    public Response editTicket(@RequestBody TicketEditRequest request){
        return ticketService.editTicket(request);
    }

    @PostMapping("/create")
    public Response addTicket(@RequestBody TicketCreationRequest request){
        return ticketService.addTicket(request);
    }

}
