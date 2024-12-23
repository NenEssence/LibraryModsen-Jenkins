package com.testproject.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI gateWayOpenApi() {
        return new OpenAPI().info(new Info().title("Demo Application Microservices APIs ")
                .description("Documentation for all the Microservices in Demo Application")
                .version("v1.0.0"));
    }
}
