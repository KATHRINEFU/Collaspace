package org.mercury.OAuthServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * @ClassName PasswordConfig
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 10:06 PM
 * @Version 1.0
 **/

@Configuration
public class PasswordConfig {
    /**
     * The passwords used in the application will be stored in memory and in the database using the BCrypt encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
