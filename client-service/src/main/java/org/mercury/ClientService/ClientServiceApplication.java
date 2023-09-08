package org.mercury.ClientService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName org.mercury.ClientService.ClientServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 2:47 PM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class ClientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
}
