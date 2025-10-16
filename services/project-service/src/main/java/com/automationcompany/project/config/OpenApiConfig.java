package com.automationcompany.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Service API")
                        .version("1.0")
                        .description("API for managing projects"))
                .servers(List.of(
                        new Server().url("/project-service").description("Via API Gateway"),
                        new Server().url("http://localhost:8088").description("Direct Access (Dev only)")
                ));
    }
}