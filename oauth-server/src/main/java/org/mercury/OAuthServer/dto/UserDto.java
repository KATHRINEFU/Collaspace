package org.mercury.OAuthServer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserDto
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 10:13 PM
 * @Version 1.0
 **/

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UserDto {
    private long id;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private char[] password;
}
