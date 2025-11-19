package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.ProjectTechnology;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        description = "Data transfer object for creating a new project. Used in project creation endpoints.",
        name = "ProjectCreateDto"
)
public class ProjectCreateDto {

    @NotBlank(message = "Project name is required")
    @Size(max = 200, message = "Project name must not exceed 200 characters")
    @Schema(
            description = "Full name of the project",
            example = "Modernizacja systemu ERP",
            maxLength = 200
    )
    private String name;

    @Size(max = 50, message = "Project code must not exceed 50 characters")
    @Schema(
            description = "Unique project code (optional, e.g., ERP-2025-01)",
            example = "ERP-2025-01",
            maxLength = 50,
            nullable = true
    )
    private String code;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Schema(
            description = "Detailed project description (optional)",
            example = "Modernizacja systemu ERP w celu zwiększenia wydajności procesów finansowych.",
            maxLength = 1000,
            nullable = true
    )
    private String description;

    @NotNull(message = "Start date is required")
    @Schema(
            description = "Project start date",
            example = "2025-03-01"
    )
    private LocalDate startDate;

    @Schema(
            description = "Planned project end date (optional)",
            example = "2025-12-31",
            nullable = true
    )
    private LocalDate endDate;

    @NotNull(message = "Status is required")
    @Schema(
            description = "Current status of the project",
            example = "PLANNING",
            implementation = ProjectStatus.class
    )
    private ProjectStatus status;

    @Schema(
            description = "Priority level of the project (optional, defaults to MEDIUM if not set)",
            example = "HIGH",
            nullable = true,
            implementation = ProjectPriority.class
    )
    private ProjectPriority priority;

    @NotNull(message = "Service type is required")
    @Schema(
            description = "Type of service provided in the project",
            example = "MACHINE_DESIGN",
            implementation = ProjectServiceType.class
    )
    private ProjectServiceType serviceType;

    @Valid
    @Schema(
            description = "Location details (can be existing location ID or new location data)",
            nullable = true
    )
    private LocationDto locationDto;

    @Schema(
            description = "Set of employee IDs assigned to the project (optional)",
            example = "[101, 102, 103]",
            nullable = true
    )
    private List<Long> employeeIds;

    @Positive(message = "Project manager ID must be positive")
    @Schema(
            description = "ID of the employee assigned as project manager (optional)",
            example = "201",
            nullable = true
    )
    private Long projectManagerId;

    @Schema(
            description = "List of technologies used in the project (optional)",
            example = "[\"SIEMENS_S7_1500\", \"SCADA\", \"HMI\"]",
            nullable = true
    )
    private List<ProjectTechnology> technologies;
}