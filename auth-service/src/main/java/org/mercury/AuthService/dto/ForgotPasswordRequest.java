package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName ForgotPasswordRequest
 * @Description TODO
 * @Author katefu
 * @Date 12/28/23 2:08 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ForgotPasswordRequest {
    private String email;
}
