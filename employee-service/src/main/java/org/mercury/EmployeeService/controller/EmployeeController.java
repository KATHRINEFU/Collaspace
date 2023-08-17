package org.mercury.EmployeeService.controller;

import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
