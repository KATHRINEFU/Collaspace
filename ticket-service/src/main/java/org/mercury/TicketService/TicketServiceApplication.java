package org.mercury.TicketService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName TicketServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 9:29 AM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class TicketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }
}
