package org.mercury.AuthService.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName UserLoginCredential
 * @Description TODO
 * @Author katefu
 * @Date 12/18/23 7:10 PM
 * @Version 1.0
 **/

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class UserLoginCredential {
    private String employeePassword;
    private String employeeEmail;
}
