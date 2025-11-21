package com.automationcompany.notification.client;

import com.automationcompany.commondomain.dto.EmployeeSimpleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceClient {

    private final WebClient employeeWebClient;

    public EmployeeSimpleDto getEmployeeBasicInfo(Long employeeId) {
        return employeeWebClient.get()
                .uri("/api/employees/{id}/simple", employeeId)
                .retrieve()
                .bodyToMono(EmployeeSimpleDto.class)
                .block();
    }
}