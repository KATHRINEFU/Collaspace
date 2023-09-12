package org.mercury.AuthService.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName UserCredential
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 9:19 PM
 * @Version 1.0
 **/

@Entity(name = "EMPLOYEE_AUTH")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {

    @Id
    @SequenceGenerator(name = "employee_auth_seq_gen", sequenceName = "EMPLOYEE_AUTH_EMPLOYEE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator="employee_auth_seq_gen", strategy = GenerationType.AUTO)
    private int employeeId;

    @Column
    private String employeePassword;

    @Column
    private String employeeEmail;
}
