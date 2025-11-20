package com.automationcompany.project.model.dto;

import com.automationcompany.commondomain.enums.PositionLevel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Represents an employee and the number of active projects they are assigned to. " +
                "Used in utilization reports and project allocation views.",
        name = "EmployeeUtilizationDto"
)
public record EmployeeUtilizationDto(

        @Schema(
                description = "Unique employee identifier from Employee Service.",
                example = "101"
        )
        Long employeeId,

        @Schema(
                description = "Employee's first name, retrieved from Employee Service.",
                example = "Jan",
                nullable = true
        )
        String firstName,

        @Schema(
                description = "Employee's last name, retrieved from Employee Service.",
                example = "Kowalski",
                nullable = true
        )
        String lastName,

        @Schema(
                description = "Employee's position level (e.g., JUNIOR, MID, SENIOR), retrieved from Employee Service.",
                example = "MID",
                nullable = true
        )
        PositionLevel positionLevel,

        @Schema(
                description = "The number of active projects the employee is currently assigned to.",
                example = "3",
                minimum = "0"
        )
        Long activeProjectCount

) {

    public EmployeeUtilizationDto(Long employeeId, Long activeProjectCount) {
        this(employeeId, null, null, null, activeProjectCount);
    }
}