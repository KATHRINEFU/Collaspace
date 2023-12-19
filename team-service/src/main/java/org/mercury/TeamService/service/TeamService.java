package org.mercury.TeamService.service;

import lombok.extern.slf4j.Slf4j;
import org.mercury.TeamService.bean.Employee;
import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.bean.TeamAccount;
import org.mercury.TeamService.bean.TeamMember;
import org.mercury.TeamService.dao.TeamAccountDao;
import org.mercury.TeamService.dao.TeamDao;
import org.mercury.TeamService.dao.TeamMemberDao;
import org.mercury.TeamService.bean.Account;
import org.mercury.TeamService.dto.TeamMemberDto;
import org.mercury.TeamService.dto.TeamRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TeamAccountDao teamAccountDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailService emailService;

    private final Map<Integer, CompletableFuture<Employee>> employeeIdToFutures = new ConcurrentHashMap<>();
    private final Map<Integer, CompletableFuture<Account>> accountIdToFutures = new ConcurrentHashMap<>();


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
        StringBuilder sb = new StringBuilder();
        for(String type: teamRequest.getTeamTypes()){
            sb.append(type);
            sb.append(",");
        }
        team.setTeamType(sb.toString());
        inviteTeamMember(team.getTeamName(), teamRequest.getMembers());
        return teamDao.save(team);
    }

    private void inviteTeamMember(String teamName, List<Employee> members){
        for(Employee member: members){
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", member.getEmployeeFirstname() + " " + member.getEmployeeLastname());
            placeholders.put("team_name", teamName);
            placeholders.put("action_url", "http://localhost:5174/user/dashboard");
            placeholders.put("support_email", "yuehaofu207@gmail.com");
            emailService.sendEmail(
                    "invite",
                    member.getEmployeeEmail(),
                    "CollaSpace | Team Invitation",
                    placeholders);
        }
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
        StringBuilder sb = new StringBuilder();
        for(String type: teamRequest.getTeamTypes()){
            sb.append(type);
            sb.append(",");
        }
        teamFromDB.setTeamType(sb.toString());
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

    @RabbitListener(queues = "q.return-account")
    public void onListenReturnAccount(Account account) {
        log.info("Return-Account message received: {}", account);
        int accountId = account.getAccountId();
        CompletableFuture<Account> accountFuture = accountIdToFutures.get(accountId);

        if (accountFuture != null) {
            accountFuture.complete(account);
        } else {
            log.warn("No corresponding CompletableFuture found for employeeId: {}", accountId );
        }
    }

    public CompletableFuture<List<Account>> getAccountsByTeamId(int teamId) {
        Team team = teamDao.findById(teamId).orElse(null);
        if(team == null) return CompletableFuture.completedFuture(null);
        List<TeamAccount> accounts= teamAccountDao.findAllByTeam(team);
        if(accounts==null || accounts.isEmpty()) return CompletableFuture.completedFuture(null);

        List<CompletableFuture<Account>> accountFutures = new ArrayList<>();
        for(TeamAccount account : accounts){
            CompletableFuture<Account> accountFuture = new CompletableFuture<>();
            int accountId = account.getAccountId();
            accountIdToFutures.put(accountId, accountFuture);

            rabbitTemplate.convertAndSend("", "q.get-account", accountId);
            accountFutures.add(accountFuture);
        }

        return CompletableFuture.allOf(accountFutures.toArray(new CompletableFuture[0]))
                .thenApply(voidResult ->
                        accountFutures.stream()
                                .map(future -> future.exceptionally(e -> null)) // Handle exceptions by returning null
                                .map(CompletableFuture::join)
                                .filter(Objects::nonNull) // Filter out null results
                                .collect(Collectors.toList()));
    }

    public List<Team> getByEmployeeId(int id) {
        List<TeamMember> teamMemberList = teamMemberDao.findAllByEmployeeId(id);
        List<Team> teamList = new ArrayList<>();
        if(!teamMemberList.isEmpty()){
            for(TeamMember teamMember : teamMemberList){
                teamList.add(teamMember.getTeam());
            }
        }
        log.info("Got teams by employee " + id + ": size of "+ teamList.size());
        return teamList;
    }
}
