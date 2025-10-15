package com.automationcompany.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient employeeWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("http://localhost:8080/employee-service/api/employees")
                               .build();
    }
}