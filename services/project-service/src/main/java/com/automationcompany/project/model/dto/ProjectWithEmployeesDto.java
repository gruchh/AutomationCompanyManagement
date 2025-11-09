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
        description = "Complete project representation including full employee details (with personal and contact info) " +
                "and project manager. Used in detailed project views, reports, and employee assignment management.",
        name = "ProjectWithEmployeesDto"
)
public class ProjectWithEmployeesDto {

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
            example = "MACHINE_DESIGN",
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
            description = "Set of employees currently assigned to the project (with full details: personal, contact, position, etc.)",
            nullable = true
    )
    private Set<EmployeeDto> employees;

    @Schema(
            description = "Project manager with full employee details (personal, contact, position, etc.)",
            nullable = true
    )
    private EmployeeDto projectManager;

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
}