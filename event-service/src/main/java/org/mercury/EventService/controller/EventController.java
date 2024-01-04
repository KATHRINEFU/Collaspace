package org.mercury.EventService.controller;

import org.mercury.EventService.bean.Event;
import org.mercury.EventService.dto.EventEditRequest;
import org.mercury.EventService.dto.EventRequest;
import org.mercury.EventService.filter.EventFilter;
import org.mercury.EventService.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName EventController
 * @Description TODO
 * @Author katefu
 * @Date 8/27/23 7:25 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable int id){
        return eventService.getById(id);
    }

    @GetMapping("/bytype/{type}")
    public List<Event> getEventByType(@PathVariable String type){
        return eventService.getByType(type);
    }

    @GetMapping("/bycreator/{employeeId}")
    public List<Event> getEventByCreatorId(@PathVariable int employeeId){
        return eventService.getByCreator(employeeId);
    }

    @GetMapping("/bycreationteamid/{teamId}")
    public List<Event> getEventByCreationTeamId(@PathVariable int teamId){
        return eventService.getByCreationTeamId(teamId);
    }

    @GetMapping("/byteam/{teamId}")
    public List<Event> getEventByTeamId(@PathVariable int teamId){
        return eventService.getByTeamId(teamId);
    }



    @GetMapping("/filter")
    public List<Event> getEventWithFilter(@RequestBody EventFilter eventFilter){
        return eventService.getWithFilter(eventFilter);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody EventRequest eventRequest) {
        try {
            Event addedEvent = eventService.addEvent(eventRequest);
            if (addedEvent != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Event added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add event");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editEvent(@PathVariable int id, @RequestBody EventEditRequest editRequest) {
        try {
            Event editedEvent = eventService.editEvent(id, editRequest);
            if (editedEvent != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Event edited successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
