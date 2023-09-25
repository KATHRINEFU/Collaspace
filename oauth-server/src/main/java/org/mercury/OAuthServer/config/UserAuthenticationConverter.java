package org.mercury.OAuthServer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.mercury.OAuthServer.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName UserAuthenticationConverter
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 10:12 PM
 * @Version 1.0
 **/
@Component
public class UserAuthenticationConverter implements AuthenticationConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Authentication convert(HttpServletRequest request) {
        UserDto userDto = null;
        try {
            userDto = MAPPER.readValue(request.getInputStream(), UserDto.class);
        } catch (IOException e) {
            return null;
        }
        return UsernamePasswordAuthenticationToken.unauthenticated(userDto.getEmail(), userDto.getPassword());
    }
}