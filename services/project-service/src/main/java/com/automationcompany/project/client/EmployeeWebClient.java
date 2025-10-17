package com.automationcompany.project.client;

import com.automationcompany.project.model.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeWebClient {

    private final WebClient employeeWebClient;

    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return employeeWebClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Employee not found: " + id)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(EmployeeDto.class)
                .onErrorReturn(null)
                .blockOptional();
    }

    public List<EmployeeDto> getEmployeesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return employeeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/batch")
                        .queryParam("ids", ids)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Failed to fetch employees")))
                .bodyToFlux(EmployeeDto.class)
                .collectList()
                .onErrorReturn(Collections.emptyList())
                .block();
    }
}