package org.mercury.GatewayClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName GatewayClientApplication
 * @Description TODO
 * @Author katefu
 * @Date 9/24/23 10:24 PM
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayClientApplication.class, args);
    }

}
