package org.mercury.EmployeeService.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName Department
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 5:47 PM
 * @Version 1.0
 **/
@Entity
@Table(name = "DEPARTMENT")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Department {
    @Id
    private int departmentId;

    @Column
    private String departmentName;
}
