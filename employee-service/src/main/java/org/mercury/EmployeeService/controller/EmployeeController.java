package org.mercury.EmployeeService.controller;

import lombok.extern.slf4j.Slf4j;
import org.mercury.EmployeeService.bean.Client;
import org.mercury.EmployeeService.bean.Department;
import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.bean.Team;
import org.mercury.EmployeeService.dto.EmployeeDashboard;
import org.mercury.EmployeeService.dto.EmployeeEditRequest;
import org.mercury.EmployeeService.dto.EmployeeRegistration;
import org.mercury.EmployeeService.dto.UpdateProfileRequest;
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

    @GetMapping("/bydepartment/{id}")
    public List<Employee> getEmployeeByDepartment(@PathVariable int id){
        return employeeService.getByDepartmentId(id);
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
    public List<Team> getEmployeeTeamsData(@PathVariable int id){
        return employeeService.getEmployeeTeams(id);
    }
    @PostMapping("/updateprofile")
    public ResponseEntity<String> updateProfileImage(@RequestBody UpdateProfileRequest request){
        try{
            employeeService.updateProfileImage(request);
            return ResponseEntity.status(HttpStatus.OK).body("Profile image updated successfully");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

//    @GetMapping("/clients/{id}")
//    public CompletableFuture<List<Client>> getEmployeeClientsData(@PathVariable int id){
//        return employeeService.sendRequestForClientsData(id)
//                .thenApply(clients -> {
//                    return clients;
//                });
//    }

    @PostMapping("/create")
    @CrossOrigin
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRegistration employeeRegistration) {
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

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editEmployee(@PathVariable int id, @RequestBody EmployeeEditRequest request){
        try {
            Employee editedEmployee = employeeService.editById(id, request);
            if (editedEmployee != null) {
                return ResponseEntity.status(HttpStatus.OK).body("Employee profile updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee failed to update");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
