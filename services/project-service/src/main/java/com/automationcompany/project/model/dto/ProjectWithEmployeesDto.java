package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectWithEmployeesDto {
    private Long id;
    private String name;
    private String code;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private ProjectPriority priority;
    private ProjectServiceType serviceType;
    private String location;
    private Set<EmployeeDto> employees;
    private EmployeeDto projectManager;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}