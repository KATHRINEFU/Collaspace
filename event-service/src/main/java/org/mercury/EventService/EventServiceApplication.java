package org.mercury.EventService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName EventServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 8/28/23 10:16 AM
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class EventServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }
}
