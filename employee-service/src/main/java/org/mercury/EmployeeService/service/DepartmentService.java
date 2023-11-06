package org.mercury.EmployeeService.service;

import org.mercury.EmployeeService.bean.Department;
import org.mercury.EmployeeService.dao.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName DepartmentService
 * @Description TODO
 * @Author katefu
 * @Date 11/1/23 11:03 AM
 * @Version 1.0
 **/

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;


    public Department getById(int id) {
        return departmentDao.findById(id).orElse(null);
    }
}
