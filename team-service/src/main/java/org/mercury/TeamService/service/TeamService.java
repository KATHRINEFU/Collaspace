package org.mercury.TeamService.service;

import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.dao.TeamDao;
import org.mercury.TeamService.dto.TeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName TeamService
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 10:19 AM
 * @Version 1.0
 **/

@Service
public class TeamService {

    @Autowired
    private TeamDao teamDao;

    public List<Team> getAll() {
        return teamDao.findAll();
    }


    public Team getById(int id) {
        return teamDao.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Team addTeam(TeamRequest teamRequest) {
        Team team = new Team();
        team.setTeamName(teamRequest.getTeamName());
        team.setTeamCreator(teamRequest.getTeamCreator());
        team.setTeamCreationdate(new Date());
        team.setTeamDescription(teamRequest.getTeamDescription());
        team.setTeamType(teamRequest.getTeamType());
        return teamDao.save(team);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Team editTeam(int id, TeamRequest teamRequest) {
        // can edit teamName, teamDescription, teamType
        Team teamFromDB = null;
        Optional<Team> optionalTeam = teamDao.findById(id);
        if(optionalTeam.isEmpty()) return null;
        teamFromDB = optionalTeam.get();
        teamFromDB.setTeamName(teamRequest.getTeamName());
        teamFromDB.setTeamDescription(teamRequest.getTeamDescription());
        teamFromDB.setTeamType(teamRequest.getTeamType());
        return teamDao.save(teamFromDB);
    }
}
