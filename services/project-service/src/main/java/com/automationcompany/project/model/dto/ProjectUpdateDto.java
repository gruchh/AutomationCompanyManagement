package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object for updating an existing project. All fields are optional – only provided values will be updated.",
        name = "ProjectUpdateDto"
)
public class ProjectUpdateDto {

    @Size(max = 200, message = "Project name must not exceed 200 characters")
    @Schema(
            description = "Updated project name (optional)",
            example = "ERP System Modernization – Phase 2",
            maxLength = 200,
            nullable = true
    )
    private String name;

    @Size(max = 50, message = "Project code must not exceed 50 characters")
    @Schema(
            description = "Updated project code (optional)",
            example = "ERP-2025-02",
            maxLength = 50,
            nullable = true
    )
    private String code;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Schema(
            description = "Updated project description (optional)",
            example = "Phase two of the ERP system modernization – implementation of the financial module.",
            maxLength = 1000,
            nullable = true
    )
    private String description;

    @Schema(
            description = "Updated project start date (optional)",
            example = "2025-06-01",
            nullable = true
    )
    private LocalDate startDate;

    @Schema(
            description = "Updated planned end date (optional, null to clear)",
            example = "2026-03-31",
            nullable = true
    )
    private LocalDate endDate;

    @Schema(
            description = "Updated project status (optional)",
            example = "IN_PROGRESS",
            nullable = true,
            implementation = ProjectStatus.class
    )
    private ProjectStatus status;

    @Schema(
            description = "Updated project priority (optional)",
            example = "MEDIUM",
            nullable = true,
            implementation = ProjectPriority.class
    )
    private ProjectPriority priority;

    @Schema(
            description = "Updated service type (optional)",
            example = "PRODUCTION_SUPPORT",
            nullable = true,
            implementation = ProjectServiceType.class
    )
    private ProjectServiceType serviceType;

    @Size(max = 150, message = "Location must not exceed 150 characters")
    @Schema(
            description = "Updated project location (optional)",
            example = "Krakow, Regional Office",
            maxLength = 150,
            nullable = true
    )
    private String location;

    @Schema(
            description = "Updated set of employee IDs assigned to the project (optional, replaces current assignment)",
            example = "[101, 103, 105]",
            nullable = true
    )
    private Set<Long> employeeIds;

    @Schema(
            description = "Updated project manager ID (optional, null to remove)",
            example = "203",
            nullable = true
    )
    private Long projectManagerId;
}