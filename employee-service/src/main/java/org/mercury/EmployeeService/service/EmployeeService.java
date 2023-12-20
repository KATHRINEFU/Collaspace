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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

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
    private EmailService emailService;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public EmployeeService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

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
        try{
            List<Department> departments = departmentDao.findAllByDepartmentName(employeeRegistration.getDepartment());
            employee.setDepartmentId(departments.get(0).getDepartmentId());

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", employee.getEmployeeFirstname() + " " + employee.getEmployeeLastname());
            placeholders.put("action_url", "http://localhost:5174");
            placeholders.put("support_email", "yuehaofu207@gmail.com");
            emailService.sendEmail(
                    "success",
                    employee.getEmployeeEmail(),
                    "CollaSpace | Registration Confirmation",
                    placeholders);
            return employeeDao.save(employee);
        }catch (Exception e){
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", employee.getEmployeeFirstname() + " " + employee.getEmployeeLastname());
            placeholders.put("error_message", e.getMessage());
            placeholders.put("action_url", "http://localhost:5174");
            placeholders.put("support_email", "yuehaofu207@gmail.com");
            emailService.sendEmail(
                    "error",
                    employee.getEmployeeEmail(),
                    "CollaSpace | Registration Confirmation",
                    placeholders);
            return null;
        }
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

    public List<Team> getEmployeeTeams(int id) {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://localhost:8080/team/byemployee/" + id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Team>>() {})
                .block();
    }

    public void updateProfileImage(UpdateProfileRequest request) {
        Employee employee  = employeeDao.findById(request.getEmployeeId()).orElse(null);
        if(employee == null){
            throw new RuntimeException("Employee Not Found");
        }
        System.out.println("Setting new image url: " + request.getNewImageUrl() );
        employee.setEmployeeProfileUrl(request.getNewImageUrl());
        employeeDao.save(employee);
    }
}