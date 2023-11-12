package org.mercury.TeamService.service;

import lombok.extern.slf4j.Slf4j;
import org.mercury.TeamService.bean.Employee;
import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.bean.TeamMember;
import org.mercury.TeamService.dao.TeamDao;
import org.mercury.TeamService.dao.TeamMemberDao;
import org.mercury.TeamService.dto.EmployeeGetTeamsReturn;
import org.mercury.TeamService.dto.TeamMemberDto;
import org.mercury.TeamService.dto.TeamRequest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName TeamService
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 10:19 AM
 * @Version 1.0
 **/

@Service
@Slf4j
public class TeamService {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final Map<Integer, CompletableFuture<Employee>> employeeIdToFutures = new ConcurrentHashMap<>();


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


    @RabbitListener(queues = "q.return-employee")
    public void onListenReturnEmployee(Employee employee) {
        log.info("Return-Employee message received: {}", employee.getEmployeeId());
        int employeeId = employee.getEmployeeId();
        CompletableFuture<Employee> employeeFuture = employeeIdToFutures.get(employeeId);

        if (employeeFuture != null) {
            employeeFuture.complete(employee);
        } else {
            log.warn("No corresponding CompletableFuture found for employeeId: {}", employeeId );
        }
    }

    public CompletableFuture<List<TeamMemberDto>> getMembersByTeamId(int teamId) {
        Team team = teamDao.findById(teamId).orElse(null);
        if(team == null) return CompletableFuture.completedFuture(null);
        List<TeamMember> members= teamMemberDao.findAllByTeam(team);
        if(members==null || members.isEmpty()) return CompletableFuture.completedFuture(null);

        // for each member, send message to a new rabbitmq queue

        List<CompletableFuture<TeamMemberDto>> memberFutures = new ArrayList<>();

        for(TeamMember member: members) {
            CompletableFuture<TeamMemberDto> memberFuture = new CompletableFuture<>();
            CompletableFuture<Employee> employeeFuture = new CompletableFuture<>();
            int employeeId = member.getEmployeeId();
            employeeIdToFutures.put(employeeId, employeeFuture);

            rabbitTemplate.convertAndSend("", "q.get-employee", employeeId);

            employeeFuture
                    .whenComplete((employee, throwable) -> {
                        if (throwable != null) {
                            memberFuture.completeExceptionally(throwable);
                        } else if (employee != null) {
                            TeamMemberDto teamMemberDto = new TeamMemberDto();
                            // Map employee and team member information to TeamMemberDto
                            teamMemberDto.setEmployeeId(employee.getEmployeeId());
                            teamMemberDto.setEmployeeFirstname(employee.getEmployeeFirstname());
                            teamMemberDto.setEmployeeLastname(employee.getEmployeeLastname());
                            teamMemberDto.setEmployeeEmail(employee.getEmployeeEmail());
                            teamMemberDto.setEmployeeLocationCountry(employee.getEmployeeLocationCountry());
                            teamMemberDto.setEmployeeLocationCity(employee.getEmployeeLocationCity());
                            teamMemberDto.setDepartmentId(employee.getDepartmentId());
                            teamMemberDto.setEmployeePhone(employee.getEmployeePhone());
                            teamMemberDto.setEmployeeBirthday(employee.getEmployeeBirthday());
                            teamMemberDto.setEmployeeRole(employee.getEmployeeRole());
                            teamMemberDto.setEmployeeManager(employee.getEmployeeManager());
                            teamMemberDto.setEmployeeStartdate(employee.getEmployeeStartdate());
                            teamMemberDto.setEmployeeProfileUrl(employee.getEmployeeProfileUrl());

                            teamMemberDto.setJoindate(member.getJoindate());
                            teamMemberDto.setRole(member.getRole());
                            memberFuture.complete(teamMemberDto);
                        } else {
                            memberFuture.completeExceptionally(new RuntimeException("Received null response from q.return-employee"));
                        }
                    });
            memberFutures.add(memberFuture);
        }

        return CompletableFuture.allOf(memberFutures.toArray(new CompletableFuture[0]))
                .thenApply(voidResult ->
                        memberFutures.stream()
                                .map(future -> future.exceptionally(e -> null)) // Handle exceptions by returning null
                                .map(CompletableFuture::join)
                                .filter(Objects::nonNull) // Filter out null results
                                .collect(Collectors.toList()));
    }
}
