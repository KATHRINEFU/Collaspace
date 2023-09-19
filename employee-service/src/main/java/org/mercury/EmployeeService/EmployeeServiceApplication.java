package org.mercury.EmployeeService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName org.mercury.EmployeeService.EmployeeServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 2:47 PM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "EmployeeServiceAPI", description = "Employee Service API v1.0"))
public class EmployeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }
}
