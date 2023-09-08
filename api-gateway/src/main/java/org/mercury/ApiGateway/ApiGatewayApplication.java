package org.mercury.ApiGateway;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import reactor.netty.http.client.HttpClient;

/**
 * @ClassName ApiGatewayApplication
 * @Description TODO
 * @Author katefu
 * @Date 9/8/23 2:33 PM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
