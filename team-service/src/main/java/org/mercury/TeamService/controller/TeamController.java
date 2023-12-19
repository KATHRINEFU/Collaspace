package org.mercury.TeamService.controller;

import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.bean.Account;
import org.mercury.TeamService.dto.TeamMemberDto;
import org.mercury.TeamService.dto.TeamRequest;
import org.mercury.TeamService.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName TeamController
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 10:39 AM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/")
    public List<Team> getAllTeams(){
        return teamService.getAll();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable int id){
        return teamService.getById(id);
    }

    @GetMapping("/byemployee/{id}")
    public List<Team> getTeamsByEmployeeId(@PathVariable int id){
        return teamService.getByEmployeeId(id);
    }

    @GetMapping("/members/{id}")
    public CompletableFuture<List<TeamMemberDto>> getTeamMembers(@PathVariable int id){
        return teamService.getMembersByTeamId(id);
    }

    @GetMapping("/accounts/{id}")
    public CompletableFuture<List<Account>> getTeamAccounts(@PathVariable int id){
        return teamService.getAccountsByTeamId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> addTeam(@RequestBody TeamRequest teamRequest) {
        try {
            Team addedTeam = teamService.addTeam(teamRequest);
            if (addedTeam != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Team created successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create team");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editEvent(@PathVariable int id, @RequestBody TeamRequest teamRequest) {
        try {
            Team editedEvent = teamService.editTeam(id, teamRequest);
            if (editedEvent != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Team edited successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
