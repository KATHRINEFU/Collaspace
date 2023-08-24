package org.mercury.TicketService.controller;

import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.bean.TicketLog;
import org.mercury.TicketService.dto.TicketLogDTO;
import org.mercury.TicketService.http.Response;
import org.mercury.TicketService.requestObjects.TicketLogCreateRequest;
import org.mercury.TicketService.service.TicketLogService;
import org.mercury.TicketService.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TicketLogController
 * @Description TODO
 * @Author katefu
 * @Date 8/23/23 7:46 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/ticket/log")
public class TicketLogController {
    @Autowired
    private TicketLogService ticketLogService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/byticket/{id}")
    public List<TicketLogDTO> getAllLogByTicketId(@PathVariable int id) {
        List<TicketLog> ticketLogs = ticketLogService.getAllByTicketId(id);
        return ticketLogs.stream()
                .map(TicketLogDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/byid/{id}")
    public TicketLogDTO getLogById(@PathVariable int id) {
        TicketLog ticketLog = ticketLogService.getById(id);
        return new TicketLogDTO(ticketLog);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTicketLog(@RequestBody TicketLogCreateRequest request) {

        Ticket ticket = ticketService.getById(request.getTicketId());
        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ticket ID");
        }

        TicketLog createdLog = ticketLogService.addTicketLog(ticket, request.getTicketLogCreator(), request.getTicketLogContent());
        if (createdLog != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Ticket log created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create ticket log");
        }
    }

    @PutMapping("/edit/{ticketLogId}")
    public ResponseEntity<String> editTicketLog(@PathVariable int ticketLogId, @RequestBody String newContent){
        TicketLog editedLog = ticketLogService.editTicketLog(ticketLogId, newContent);
        if (editedLog != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Ticket log content edited successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket log not found");
        }
    }
}
