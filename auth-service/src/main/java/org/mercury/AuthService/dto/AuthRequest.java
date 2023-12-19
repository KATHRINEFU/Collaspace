package org.mercury.AuthService.dto;

import lombok.*;

/**
 * @ClassName AuthRequest
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 9:43 PM
 * @Version 1.0
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

}
