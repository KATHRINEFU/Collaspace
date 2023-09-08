package org.mercury.DiscoveryServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName DiscoveryServerApplication
 * @Description TODO
 * @Author katefu
 * @Date 9/7/23 5:37 PM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }


}
