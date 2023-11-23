package org.mercury.EmployeeService.controller;

import lombok.extern.slf4j.Slf4j;
import org.mercury.EmployeeService.bean.Client;
import org.mercury.EmployeeService.bean.Department;
import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.bean.Team;
import org.mercury.EmployeeService.dto.EmployeeDashboard;
import org.mercury.EmployeeService.dto.EmployeeRegistration;
import org.mercury.EmployeeService.filter.EmployeeFilter;
import org.mercury.EmployeeService.service.DepartmentService;
import org.mercury.EmployeeService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName EmployeeController
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 2:49 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/all")
    public List<Employee> getAll(){

        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        return employeeService.getById(id);
    }

    @GetMapping("/department/{id}")
    public Department getDepartmentById(@PathVariable int id){
        return departmentService.getById(id);
    }

    @GetMapping("/filter")
    public List<Employee> getEmployeeWithFilter(@RequestBody EmployeeFilter employeeFilter){
        // can be filtered by location, departmentId, startdate, role
        return employeeService.getWithFilter(employeeFilter);
    }

    @GetMapping("/teams/{id}")
    public CompletableFuture<List<Team>> getEmployeeTeamsData(@PathVariable int id){
        return employeeService.sendRequestForTeamsData(id)
                .thenApply(teams -> {
                    // Perform any additional processing if needed
                    // This block executes when all services have responded
                    return teams;
                });
    }

//    @GetMapping("/clients/{id}")
//    public CompletableFuture<List<Client>> getEmployeeClientsData(@PathVariable int id){
//        return employeeService.sendRequestForClientsData(id)
//                .thenApply(clients -> {
//                    return clients;
//                });
//    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:5174", allowedHeaders = "*", exposedHeaders = "If-Match")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRegistration employeeRegistration) {
        System.out.println("Received request for creating employee");
        log.info("Received request for creating employee");
        try {
            Employee addedEmployee = employeeService.register(employeeRegistration);
            if (addedEmployee != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Employee registered successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee failed to register");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
