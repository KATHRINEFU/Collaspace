package org.mercury.EmployeeService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @ClassName WebClientConfig
 * @Description TODO
 * @Author katefu
 * @Date 8/31/23 9:25 AM
 * @Version 1.0
 **/

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
