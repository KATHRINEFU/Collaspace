package org.mercury.EmployeeService.service;

import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName EmployeeService
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 4:48 PM
 * @Version 1.0
 **/

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAll(){
        return employeeDao.findAll();
    }
}
