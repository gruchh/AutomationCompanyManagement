package com.automationcompany.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents an employee and the number of active projects they are assigned to.")
public record EmployeeUtilizationDto(
        @Schema(description = "Unique employee identifier.")
        Long employeeId,

        @Schema(description = "Employee's first name, retrieved from Employee Service.")
        String firstName,

        @Schema(description = "Employee's last name, retrieved from Employee Service.")
        String lastName,

        @Schema(description = "Employee's position, retrieved from Employee Service.")
        String positionLevel,

        @Schema(description = "The number of active projects the employee is currently assigned to.")
        Long activeProjectCount
) {

    public EmployeeUtilizationDto(Long employeeId, Long activeProjectCount) {
        this(employeeId, null, null, null, activeProjectCount);
    }
}