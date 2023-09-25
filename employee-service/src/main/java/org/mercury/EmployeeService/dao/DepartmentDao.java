package org.mercury.EmployeeService.dao;

import org.mercury.EmployeeService.bean.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentDao extends JpaRepository<Department, Integer> {
    List<Department> findAllByDepartmentName(String department);
}
