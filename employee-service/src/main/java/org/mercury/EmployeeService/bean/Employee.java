package org.mercury.EmployeeService.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName Employee
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 4:05 PM
 * @Version 1.0
 **/

@Entity
@Table(name = "EMPLOYEE")
@Getter @Setter @NoArgsConstructor
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "EMPLOYEE_EMPLOYEE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="employee_seq_gen", strategy = GenerationType.AUTO)
    private int employeeId;
    @Column
    private String employeeFirstname;
    @Column
    private String employeeLastname;
    @Column
    private String employeeEmail;
    @Column
    private String employeePhone;
    @Column
    private String departmentId;
    @Column
    private Date employeeBirthday;
    @Column
    private Date employeeStartdate;
    @Column
    private String employeeRole;
    @Column
    private Integer employeeManager;
}
