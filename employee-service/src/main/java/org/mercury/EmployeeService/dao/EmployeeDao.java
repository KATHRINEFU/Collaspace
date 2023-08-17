package org.mercury.EmployeeService.dao;

import org.mercury.EmployeeService.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {

}
