package org.mercury.ApiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author katefu
 * @Date 9/12/23 10:18 AM
 * @Version 1.0
 **/
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
}
