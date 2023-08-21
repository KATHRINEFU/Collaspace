package org.mercury.EmployeeService.controller;

import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.filter.EmployeeFilter;
import org.mercury.EmployeeService.http.Response;
import org.mercury.EmployeeService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName EmployeeController
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 2:49 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public List<Employee> getAll(){
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        return employeeService.getById(id);
    }

    @GetMapping("/filter")
    public List<Employee> getEmployeeWithFilter(@RequestBody EmployeeFilter employeeFilter){
        // can be filtered by location, departmentId, startdate, role
        return employeeService.getWithFilter(employeeFilter);
    }
    @PostMapping("/create")
    public Response addEmployee(@RequestBody Employee employee){
        return employeeService.register(employee);
    }
}
