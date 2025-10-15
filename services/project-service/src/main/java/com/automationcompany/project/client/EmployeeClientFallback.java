package com.automationcompany.project.client;

import com.automationcompany.project.model.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EmployeeClientFallback implements EmployeeClient {

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.warn("Fallback: Unable to fetch employee with id: {}", id);
        return EmployeeDto.builder()
                .id(id)
                .firstName("Unknown")
                .lastName("Unknown")
                .email("unavailable@example.com")
                .status("UNAVAILABLE")
                .build();
    }

    @Override
    public List<EmployeeDto> getEmployeesByIds(List<Long> ids) {
        log.warn("Fallback: Unable to fetch employees with ids: {}", ids);
        return ids.stream()
                .map(this::getEmployeeById)
                .collect(Collectors.toList());
    }
}
