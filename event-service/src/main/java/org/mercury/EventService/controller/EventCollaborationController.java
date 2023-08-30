package org.mercury.EventService.controller;

import org.mercury.EventService.bean.EventCollaboration;
import org.mercury.EventService.dto.EventCollaborationRequest;
import org.mercury.EventService.service.EventCollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName EventCollaborationController
 * @Description TODO
 * @Author katefu
 * @Date 8/29/23 9:44 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/event-collaboration")
public class EventCollaborationController {
    @Autowired
    private EventCollaborationService eventCollaborationService;

    @GetMapping("/id")
    public EventCollaboration getEventCollaborationById(@PathVariable int id){
        return eventCollaborationService.getById(id);
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<String> acceptEventCollaboration(@PathVariable int id) {
        try {
            eventCollaborationService.acceptEventCollaboration(id);
            return ResponseEntity.status(HttpStatus.OK).body("Event collaboration accepted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
