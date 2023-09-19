package org.mercury.ApiGateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * @ClassName OpenAPIConfiguration
 * @Description TODO
 * @Author katefu
 * @Date 9/14/23 10:09 AM
 * @Version 1.0
 **/

@Component
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI gateWayOpenApi() {
        return new OpenAPI().info(new Info().title("CollaSpace Application Microservices APIs ")
                .description("Documentation for all the Microservices in CollaSpace Application")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("CollaSpace Application Development Team")
                        .email("yuehaofu207@gmail.com")));
    }
}
