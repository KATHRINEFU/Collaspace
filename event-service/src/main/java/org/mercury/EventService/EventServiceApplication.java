package org.mercury.EventService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName EventServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 8/28/23 10:16 AM
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "EventServiceAPI", description = "Event Service API v1.0"))
public class EventServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5174")
                        .allowedMethods("*")
                        .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
                                "Access-Control-Request-Headers", "Access-Control-Allow-Origin")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
}
