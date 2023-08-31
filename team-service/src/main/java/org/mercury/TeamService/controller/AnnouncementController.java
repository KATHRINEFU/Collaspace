package org.mercury.TeamService.controller;

import org.mercury.TeamService.bean.Announcement;
import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.dto.AnnouncementRequest;
import org.mercury.TeamService.dto.TeamRequest;
import org.mercury.TeamService.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AnnouncementController
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 11:14 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("team/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/byteam/{teamId}")
    public List<Announcement> getAnnouncementByTeam(@PathVariable int teamId){
        return announcementService.getByTeamId(teamId);
    }

    @GetMapping("/{id}")
    public Announcement getAnnouncementById(@PathVariable int id){
        return announcementService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> addAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        try {
            Announcement addedAnnouncement = announcementService.addAnnouncement(announcementRequest);
            if (addedAnnouncement != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Announcement created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create announcement");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editAnnouncement(@PathVariable int id, @RequestBody AnnouncementRequest announcementRequest) {
        try {
            Announcement editedAnnouncement = announcementService.editAnnouncement(id, announcementRequest);
            if (editedAnnouncement != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Announcement edited successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to edit announcement");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
