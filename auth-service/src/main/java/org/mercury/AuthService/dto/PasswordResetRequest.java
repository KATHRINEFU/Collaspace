package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName PasswordResetRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/28/23 6:27 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PasswordResetRequest {
    private String email;
    private String token;
    private String newPassword;
}
