package org.mercury.ApiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

/**
 * @ClassName RouteValidator
 * @Description TODO
 * @Author katefu
 * @Date 9/12/23 10:13 AM
 * @Version 1.0
 **/
@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register", 
            "/auth/token",
            "/auth/forgot",
            "/auth/reset",
            "/employee/create",
            "/employee/login",
            "/eureka/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
