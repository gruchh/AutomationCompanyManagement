package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.Project;
import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private Long id;
    
    @NotBlank(message = "Project name is required")
    @Size(max = 200, message = "Project name must not exceed 200 characters")
    private String name;
    
    @Size(max = 50, message = "Project code must not exceed 50 characters")
    private String code;
    
    private String description;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    @NotNull(message = "Status is required")
    private ProjectStatus status;
    
    private ProjectPriority priority;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Budget must be greater than 0")
    private BigDecimal budget;
    
    @DecimalMin(value = "0.0", message = "Actual cost must be greater than or equal to 0")
    private BigDecimal actualCost;
    
    private Set<Long> employeeIds;
    
    private Long projectManagerId;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}