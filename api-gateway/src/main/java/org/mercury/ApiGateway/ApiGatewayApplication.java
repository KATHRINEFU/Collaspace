package org.mercury.ApiGateway;

import io.netty.resolver.DefaultAddressResolverGroup;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Lazy;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ApiGatewayApplication
 * @Description TODO
 * @Author katefu
 * @Date 9/8/23 2:33 PM
 * @Version 1.0
 **/

@SpringBootApplication
@EnableDiscoveryClient
//@OpenAPIDefinition(info = @Info(title = "ApiGatewayAPI", description = "API Gateway API v1.0"))
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }

//    @Bean
//    @Lazy(false)
//    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
//        List<GroupedOpenApi> groups = new ArrayList<>();
//        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
//        for (RouteDefinition definition : definitions) {
//            System.out.println("id: " + definition.getId() + "  " + definition.getUri().toString());
//        }
//        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
//            String name = routeDefinition.getId().replaceAll("-service", "");
//            GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
//        });
//        return groups;
//    }

    @Bean
    public GroupedOpenApi accountApis(){
        return GroupedOpenApi.builder().pathsToMatch("/account/**").group("account").build();
    }

    @Bean
    public GroupedOpenApi authApis(){
        return GroupedOpenApi.builder().pathsToMatch("/auth/**").group("auth").build();
    }

    @Bean
    public GroupedOpenApi clientApis(){
        return GroupedOpenApi.builder().pathsToMatch("/client/**").group("client").build();
    }

    @Bean
    public GroupedOpenApi employeeApis(){
        return GroupedOpenApi.builder().pathsToMatch("/employee/**").group("employee").build();
    }

    @Bean
    public GroupedOpenApi eventApis(){
        return GroupedOpenApi.builder().pathsToMatch("/event/**").group("event").build();
    }

    @Bean
    public GroupedOpenApi teamApis(){
        return GroupedOpenApi.builder().pathsToMatch("/team/**").group("team").build();
    }

    @Bean
    public GroupedOpenApi ticketApis(){
        return GroupedOpenApi.builder().pathsToMatch("/ticket/**").group("ticket").build();
    }
}
