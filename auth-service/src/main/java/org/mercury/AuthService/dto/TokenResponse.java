package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName TokenResponse
 * @Description TODO
 * @Author katefu
 * @Date 12/18/23 9:23 PM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TokenResponse {
    private int id;
    private String email;
    private String token;
}
