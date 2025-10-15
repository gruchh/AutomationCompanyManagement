package com.automationcompany.project.client;


import com.automationcompany.project.model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "api-gateway", path = "/employee-service", fallback = EmployeeClientFallback.class)

public interface EmployeeClient {

    @GetMapping("/api/employees/{id}")
    EmployeeDto getEmployeeById(@PathVariable Long id);

    @GetMapping("/api/employees/batch")
    List<EmployeeDto> getEmployeesByIds(@RequestParam List<Long> ids);
}