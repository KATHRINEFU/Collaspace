package org.mercury.TeamService.service;

import org.mercury.TeamService.bean.Announcement;
import org.mercury.TeamService.bean.Employee;
import org.mercury.TeamService.bean.Team;
import org.mercury.TeamService.bean.TeamMember;
import org.mercury.TeamService.dao.AnnouncementDao;
import org.mercury.TeamService.dao.TeamDao;
import org.mercury.TeamService.dto.AnnouncementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

/**
 * @ClassName AnnoucementService
 * @Description TODO
 * @Author katefu
 * @Date 8/30/23 11:13 PM
 * @Version 1.0
 **/

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private EmailService emailService;

    private final WebClient webClient;

    public AnnouncementService() {
        this.webClient = WebClient.create("http://localhost:8080/employee/");
    }

    public List<Announcement> getByTeamId(int teamId) {
        Optional<Team> optionalTeam = teamDao.findById(teamId);
        if(optionalTeam.isEmpty()) return null;
        return announcementDao.findAllByTeam(optionalTeam.get());
    }

    public Announcement getById(int id) {
        return  announcementDao.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Announcement addAnnouncement(AnnouncementRequest announcementRequest) {
        Optional<Team> optionalTeam = teamDao.findById(announcementRequest.getTeamId());
        if(optionalTeam.isEmpty()) return null;

        Team team = optionalTeam.get();

        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setAnnouncementCreator(announcementRequest.getAnnouncementCreator());
        newAnnouncement.setAnnouncementCreationdate(new Date());
        newAnnouncement.setTeam(team);
        newAnnouncement.setAnnouncementContent(announcementRequest.getAnnouncementContent());

        //TODO: get each member's employee, send email
        List<Employee> employees = new ArrayList<>();
        for(TeamMember member: team.getMembers()){
            Employee employee = webClient.get()
                    .uri("/{id}", member.getEmployeeId())
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block(); // This makes the call synchronous
            if (employee != null) {
                employees.add(employee);
            }
        }

        Employee creator = webClient.get()
                .uri("/{id}", newAnnouncement.getAnnouncementCreator())
                .retrieve()
                .bodyToMono(Employee.class)
                .block(); // This makes the call synchronous

        assert creator!=null;
        sendToTeamMember(team.getTeamName(), creator.getEmployeeFirstname() + "" + creator.getEmployeeLastname(), newAnnouncement.getAnnouncementContent(), employees);

        return announcementDao.save(newAnnouncement);
    }

    private void sendToTeamMember(String teamName, String creatorName, String content, List<Employee> members){
        for(Employee member: members){
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", member.getEmployeeFirstname() + " " + member.getEmployeeLastname());
            placeholders.put("team_name", teamName);
            placeholders.put("creator_name", creatorName);
            placeholders.put("content", content);
            placeholders.put("action_url", "http://localhost:5174/user/dashboard");
            placeholders.put("support_email", "yuehaofu207@gmail.com");
            emailService.sendEmail(
                    "announcement",
                    member.getEmployeeEmail(),
                    "CollaSpace | Team Announcement",
                    placeholders);
        }
    }

    public Announcement editAnnouncement(int id, AnnouncementRequest announcementRequest) {
        // can edit content
        Optional<Announcement> optionalAnnouncement = announcementDao.findById(id);
        if(optionalAnnouncement.isEmpty()) return null;

        Announcement announcementFromDB = optionalAnnouncement.get();
        announcementFromDB.setAnnouncementContent(announcementFromDB.getAnnouncementContent());

        return announcementDao.save(announcementFromDB);
    }

    public List<Announcement> getInSevenDaysByTeamId(int teamId) {
        Date currentDate = new Date();

        // Calculate the date seven days ago
        Calendar sevenDaysAgo = Calendar.getInstance();
        sevenDaysAgo.setTime(currentDate);
        sevenDaysAgo.add(Calendar.DAY_OF_MONTH, -7);

        // Retrieve announcements for the given team with creation dates in the past seven days
        Team team = teamDao.findById(teamId).orElse(null);
        if(team==null) return null;


        return announcementDao.findAllByTeamAndAnnouncementCreationdateGreaterThanEqual(
                team, sevenDaysAgo.getTime());

    }
}
