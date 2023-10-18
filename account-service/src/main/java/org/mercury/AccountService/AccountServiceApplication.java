package org.mercury.AccountService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springdoc.core.utils.Constants.ALL_PATTERN;

/**
 * @ClassName org.mercury.AccountService.AccountServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 9/4/23 2:47 PM
 * @Version 1.0
 **/

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "AccountServiceAPI", description = "Account Service API v1.0"))
@EnableDiscoveryClient
public class AccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
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
    @Bean
    public GroupedOpenApi accountApis(){
        return GroupedOpenApi.builder().pathsToMatch("/account/**").group("account").build();
    }

//    @Bean
//    public GroupedOpenApi actuatorApi(OpenApiCustomizer actuatorOpenApiCustomiser,
//                                      WebEndpointProperties endpointProperties,
//                                      @Value("${springdoc.version}") String appVersion) {
//        return GroupedOpenApi.builder()
//                .group("Actuator")
//                .pathsToMatch(endpointProperties.getBasePath() + ALL_PATTERN)
//                .addOpenApiCustomizer(actuatorOpenApiCustomiser)
//                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Actuator API").version(appVersion)))
//                .pathsToExclude("/health/*")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
//        return GroupedOpenApi.builder().group("users")
//                .addOperationCustomizer((operation, handlerMethod) -> {
//                    operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
//                    return operation;
//                })
//                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("AccountService API").version(appVersion)))
//                .packagesToScan("org.mercury.AccountService")
//                .build();
//    }
}
