package org.mercury.TeamService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName org.mercury.TeamService.TeamServiceApplication
 * @Description TODO
 * @Author katefu
 * @Date 8/17/23 2:47 PM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class TeamServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamServiceApplication.class, args);
    }
}
