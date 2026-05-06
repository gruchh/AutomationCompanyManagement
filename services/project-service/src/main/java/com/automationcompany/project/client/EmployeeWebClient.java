package com.automationcompany.project.client;

import com.automationcompany.commondomain.dto.EmployeeReadDto;
import com.automationcompany.project.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeWebClient {

    private final WebClient employeeWebClient;

    public Mono<EmployeeReadDto> getEmployeeById(Long id) {
        return employeeWebClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new EmployeeNotFoundException("Employee not found: " + id)))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(EmployeeReadDto.class);
    }

    public Flux<EmployeeReadDto> getEmployeesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }

        return employeeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/batch")
                        .queryParam("ids", ids)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Failed to fetch employees")))
                .bodyToFlux(EmployeeReadDto.class);
    }

    public Flux<EmployeeReadDto> getAllEmployees() {
        return employeeWebClient.get()
                .uri("")
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Failed to fetch employees")))
                .bodyToFlux(EmployeeReadDto.class);
    }
}