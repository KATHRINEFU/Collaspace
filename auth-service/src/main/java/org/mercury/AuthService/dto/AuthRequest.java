package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName AuthRequest
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 9:43 PM
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

}
