package org.mercury.TeamService.listener;

import lombok.extern.slf4j.Slf4j;
import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.bean.TeamMember;
import org.mercury.TeamService.dao.TeamMemberDao;
import org.mercury.TeamService.dto.EmployeeGetTeamsRequest;
import org.mercury.TeamService.dto.EmployeeGetTeamsReturn;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GetEmployeeTeamsListener
 * @Description TODO
 * @Author katefu
 * @Date 10/5/23 9:55 AM
 * @Version 1.0
 **/

@Service
@Slf4j
public class GetEmployeeTeamsListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @RabbitListener(queues = {"q.get-employee-teams"})
    public void onListenGetEmployeeTeams(EmployeeGetTeamsRequest request){
        log.info("Get-Employee-Teams request received: {}", request);
        // TODO: get teams from database where teams include current employee
        List<TeamMember> teamMemberList = teamMemberDao.findAllByEmployeeId(request.getEmployeeId());
        List<Team> teamList = new ArrayList<>();
        if(!teamMemberList.isEmpty()){
            for(TeamMember teamMember : teamMemberList){
                teamList.add(teamMember.getTeam());
            }
        }
        EmployeeGetTeamsReturn teamsReturn = new EmployeeGetTeamsReturn(teamList);
        rabbitTemplate.convertAndSend("", "q.return-employee-teams", teamsReturn);
        log.info("Return-Employee_Teams return sent: {}", teamsReturn);
    }

}
