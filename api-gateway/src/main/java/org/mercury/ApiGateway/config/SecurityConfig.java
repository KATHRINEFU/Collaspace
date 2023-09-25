package org.mercury.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 8:38 PM
 * @Version 1.0
 **/

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> {
            c.disable();
        });

        http.cors(c -> {
            c.configurationSource(corsConfigurationSource());
        });

        http.authorizeHttpRequests(request -> {
            // if else的关系，只进最近的
            // 完整url，order不包含order/*

            request
//                    .requestMatchers(HttpMethod.GET, "/products")
//                        .permitAll()
//                    .requestMatchers(HttpMethod.GET, "/orders")
//                        .hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/orders/*")
//                    .   hasAnyRole("ADMIN", "USER")
                    .anyRequest()
                    .permitAll();
        });

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5147"); // You should only set trusted site here. e.g. http://localhost:4200 means only this site can access.
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD","OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
