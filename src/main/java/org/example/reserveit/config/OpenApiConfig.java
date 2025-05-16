package org.example.reserveit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "BookMyShow API",
                version = "1.0",
                description = "API documentation for BookMyShow clone"
        ),
        servers = @Server(url = "http://localhost:8080")
)
@Configuration
public class OpenApiConfig {
}
