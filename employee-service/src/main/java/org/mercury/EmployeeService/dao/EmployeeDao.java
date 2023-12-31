package org.mercury.EmployeeService.dao;

import org.mercury.EmployeeService.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    List<Employee> findAllByDepartmentId(int id);
}