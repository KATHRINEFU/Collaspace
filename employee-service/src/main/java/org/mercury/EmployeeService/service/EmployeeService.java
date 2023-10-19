package org.mercury.EmployeeService.service;

import lombok.extern.slf4j.Slf4j;
import org.mercury.EmployeeService.bean.*;
import org.mercury.EmployeeService.criteria.SearchCriteria;
import org.mercury.EmployeeService.dao.DepartmentDao;
import org.mercury.EmployeeService.dao.EmployeeDao;
import org.mercury.EmployeeService.dto.*;
import org.mercury.EmployeeService.filter.EmployeeFilter;
import org.mercury.EmployeeService.specification.EmployeeSpecification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName EmployeeService
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 4:48 PM
 * @Version 1.0
 **/

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private CompletableFuture<List<Team>> teamsFuture;

    public List<Employee> getAll(){
        return employeeDao.findAll();
    }

    public Employee getById(int id){
        Optional<Employee> optionalEmployee = employeeDao.findById(id);
        return optionalEmployee.orElse(null);
    }

    public Employee register(EmployeeRegistration employeeRegistration){
        Employee employee = new Employee();
        employee.setEmployeeFirstname(employeeRegistration.getEmployeeFirstname());
        employee.setEmployeeLastname(employeeRegistration.getEmployeeLastname());
        employee.setEmployeeEmail(employeeRegistration.getEmployeeEmail());
        employee.setEmployeeLocationCountry(employeeRegistration.getEmployeeLocationCountry());
        employee.setEmployeeLocationCity(employeeRegistration.getEmployeeLocationCity());
        employee.setEmployeeRole(employeeRegistration.getEmployeeRole());
        employee.setEmployeePhone(employeeRegistration.getEmployeePhone());
        employee.setEmployeeStartdate(new Date());

        List<Department> departments = departmentDao.findAllByDepartmentName(employeeRegistration.getDepartment());
        employee.setDepartmentId(departments.get(0).getDepartmentId());
        //TODO: register in AuthService
        return employeeDao.save(employee);
    }

    public List<Employee> getWithFilter(EmployeeFilter employeeFilter){
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        String departmentId = employeeFilter.getDepartmentId();
        if (!departmentId.isEmpty()) {
            searchCriteriaList.add(new SearchCriteria("departmentId", ":", Integer.valueOf(departmentId)));
        }

        String employeeLocationCountry = employeeFilter.getEmployeeLocationCountry();
        if (!employeeLocationCountry.isEmpty()) {
            searchCriteriaList.add(new SearchCriteria("employeeLocationCountry", ":", employeeLocationCountry));
        }

        String employeeLocationCity = employeeFilter.getEmployeeLocationCity();
        if (!employeeLocationCity.isEmpty()) {
            searchCriteriaList.add(new SearchCriteria("employeeLocationCity", ":", employeeLocationCity));
        }

        Date employeeStartdateStart = employeeFilter.getEmployeeStartdateStart();
        if (employeeStartdateStart != null) {
            searchCriteriaList.add(new SearchCriteria("employeeStartdate", ">", employeeStartdateStart));
        }

        Date employeeStartdateEnd = employeeFilter.getEmployeeStartdateEnd();
        if (employeeStartdateEnd != null) {
            searchCriteriaList.add(new SearchCriteria("employeeStartdate", "<", employeeStartdateEnd));
        }

        String employeeRole = employeeFilter.getEmployeeRole();
        if (!employeeRole.isEmpty()) {
            searchCriteriaList.add(new SearchCriteria("employeeRole", ":", employeeRole));
        }

        List<EmployeeSpecification> specifications = searchCriteriaList.stream()
                .map(EmployeeSpecification::new).toList();

        Specification<Employee> finalSpecification = Specification.where(specifications.get(0));
        for (int i = 1; i < specifications.size(); i++) {
            finalSpecification = finalSpecification.and(specifications.get(i));
        }

        return employeeDao.findAll(finalSpecification);
    }

    @RabbitListener(queues = {"q.return-employee-teams"})
    public void onListenReturnEmployeeTeams(EmployeeGetTeamsReturn teamsReturn){
        log.info("Return-Employee_Teams message received: {}", teamsReturn.getTeamList());
        // TODO: how to add teamList to EmployeeDashboard in returnDashBoardData?
        teamsFuture.complete(teamsReturn.getTeamList());
    }


    public CompletableFuture<List<Team>> sendRequestForTeamsData(int id) {
        // TODO: send requests to team services
        List<Team> employeeTeams = new ArrayList<>();
        List<CompletableFuture<List<Team>>> futures = new ArrayList<>();


        teamsFuture = new CompletableFuture<>();
        EmployeeGetTeamsRequest teamsRequest = new EmployeeGetTeamsRequest(id);
        rabbitTemplate.convertAndSend("", "q.get-employee-teams", teamsRequest);
        futures.add(teamsFuture);

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allOfFuture.thenApply(voidResult -> {
            // Process the results from all services
            for (CompletableFuture<List<Team>> future : futures) {
                List<Team> teams = future.join(); // Get the result of each future
                for(Team team: teams){
                    for(Announcement announcement: team.getAnnouncements()){
                        Employee announcementCreator = employeeDao.findById(announcement.getAnnouncementCreator()).orElse(null);
                        if(announcementCreator!=null) announcement.setAnnouncementCreatorName(announcementCreator.getEmployeeFirstname() + " " + announcementCreator.getEmployeeLastname());
                    }
                    employeeTeams.add(team);
//                    System.out.println(team.getTeamName());
                }
            }

            return employeeTeams;
        });
    }

}
