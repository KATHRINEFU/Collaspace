package org.mercury.AccountService.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @ClassName OpenAPI3Configuration
 * @Description TODO
 * @Author katefu
 * @Date 9/14/23 10:07 AM
 * @Version 1.0
 **/

@OpenAPIDefinition(servers = { @Server(url = "https://localhost:8081")}, info = @Info(title = "Account Service APIs", description = "This lists all the Account Service API Calls. The Calls are OAuth2 secured, ",
        version = "v1.0"))
public class OpenAPI3Configuration {
}
