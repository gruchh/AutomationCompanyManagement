package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
            example = "1001",
            required = true
    )
    private Long id;

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
            description = "Unique project code (optional)",
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
            example = "2025-03-01",
            type = "string",
            format = "date"
    )
    private LocalDate startDate;

    @Schema(
            description = "Planned or actual project end date (null if not finished)",
            example = "2025-12-31",
            type = "string",
            format = "date",
            nullable = true
    )
    private LocalDate endDate;

    @NotNull(message = "Status is required")
    @Schema(
            description = "Current status of the project",
            example = "IN_PROGRESS"
    )
    private ProjectStatus status;

    @Schema(
            description = "Priority level of the project",
            example = "HIGH",
            nullable = true
    )
    private ProjectPriority priority;

    @NotNull(message = "Service type is required")
    @Schema(
            description = "Type of service provided in the project",
            example = "IMPLEMENTATION"
    )
    private ProjectServiceType serviceType;

    @Size(max = 150, message = "Location must not exceed 150 characters")
    @Schema(
            description = "Physical or logical location of the project (optional)",
            example = "Warszawa, Biuro Główne",
            maxLength = 150,
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
            example = "2025-03-01T09:15:30",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the project was last updated",
            example = "2025-03-15T14:22:10",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Username or ID of the user who created the project",
            example = "pm-jan.kowalski"
    )
    private String createdBy;

    @Schema(
            description = "Username or ID of the user who last updated the project",
            example = "pm-anna.nowak"
    )
    private String updatedBy;
}