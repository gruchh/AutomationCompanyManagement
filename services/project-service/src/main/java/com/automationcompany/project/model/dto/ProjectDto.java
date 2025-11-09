package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        description = "Complete data transfer object representing a project with all details and audit information (read-only response).",
        name = "ProjectDto"
)
public class ProjectDto {

    @Schema(
            description = "Unique identifier of the project",
            example = "1001"
    )
    private Long id;

    @Schema(
            description = "Full name of the project",
            example = "ERP System Modernization"
    )
    private String name;

    @Schema(
            description = "Unique project code (optional)",
            example = "ERP-2025-01",
            nullable = true
    )
    private String code;

    @Schema(
            description = "Detailed project description (optional)",
            example = "Modernization of the ERP system to increase the efficiency of financial processes.",
            nullable = true
    )
    private String description;

    @Schema(
            description = "Project start date",
            example = "2025-03-01"
    )
    private LocalDate startDate;

    @Schema(
            description = "Planned or actual project end date (null if not finished)",
            example = "2025-12-31",
            nullable = true
    )
    private LocalDate endDate;

    @Schema(
            description = "Current status of the project",
            example = "IN_PROGRESS",
            implementation = ProjectStatus.class
    )
    private ProjectStatus status;

    @Schema(
            description = "Priority level of the project",
            example = "HIGH",
            nullable = true,
            implementation = ProjectPriority.class
    )
    private ProjectPriority priority;

    @Schema(
            description = "Type of service provided in the project",
            example = "MACHINE_REALIZATION",
            implementation = ProjectServiceType.class
    )
    private ProjectServiceType serviceType;

    @Schema(
            description = "Physical or logical location of the project (optional)",
            example = "Warsaw, Head Office",
            nullable = true
    )
    private String location;

    @Schema(
            description = "Set of employee IDs currently assigned to the project",
            example = "[101, 102, 103]",
            nullable = true
    )
    private Set<Long> employeeIds;

    @Schema(
            description = "ID of the employee assigned as project manager",
            example = "201",
            nullable = true
    )
    private Long projectManagerId;

    @Schema(
            description = "Timestamp when the project was created",
            example = "2025-03-01T09:15:30"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the project was last updated",
            example = "2025-03-15T14:22:10"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Username or ID of the user who created the project",
            example = "pm-john.smith"
    )
    private String createdBy;

    @Schema(
            description = "Username or ID of the user who last updated the project",
            example = "pm-jane.doe"
    )
    private String updatedBy;
}