package org.mercury.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ValidationRequest
 * @Description TODO
 * @Author katefu
 * @Date 9/10/23 9:45 PM
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationRequest {
    String token;
}
