package com.automationcompany.project.model.dto;

import com.automationcompany.project.model.ProjectPriority;
import com.automationcompany.project.model.ProjectServiceType;
import com.automationcompany.project.model.ProjectStatus;
import com.automationcompany.project.model.ProjectTechnology;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Parameters for filtering projects in the public view")
public class ProjectFilterDto {

    @Schema(
            description = "List of project statuses to filter by",
            example = "[\"PLANNED\", \"IN_PROGRESS\"]"
    )
    private List<ProjectStatus> statuses;

    @Schema(
            description = "List of service types to filter by",
            example = "[\"MACHINE_REALIZATION\", \"MODERNIZATION\"]"
    )
    private List<ProjectServiceType> serviceTypes;

    @Schema(
            description = "List of project priorities to filter by",
            example = "[\"HIGH\", \"CRITICAL\"]"
    )
    private List<ProjectPriority> priorities;

    @Schema(
            description = "List of technologies or controllers to filter by",
            example = "[\"SIEMENS_S7_1500\", \"ALLEN_BRADLEY\", \"SCADA\"]"
    )
    private List<ProjectTechnology> technologies;

    @Schema(
            description = "Project location (city, region) - partial match",
            example = "Poznań"
    )
    private String location;

    @Schema(
            description = "Project start date - from",
            example = "2024-01-01"
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateFrom;

    @Schema(
            description = "Project start date - to",
            example = "2024-12-31"
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateTo;

    @Schema(
            description = "Project end date - from",
            example = "2024-06-01"
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateFrom;

    @Schema(
            description = "Project end date - to",
            example = "2025-12-31"
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateTo;

    @Schema(
            description = "Search phrase to match against project name or description",
            example = "automation"
    )
    private String searchQuery;

    @Schema(
            description = "Minimum team size",
            example = "3"
    )
    private Integer minTeamSize;

    @Schema(
            description = "Maximum team size",
            example = "10"
    )
    private Integer maxTeamSize;

    @Schema(
            description = "Sort by field: startDate, name, teamSize, status",
            example = "startDate",
            defaultValue = "startDate"
    )
    private String sortBy;

    @Schema(
            description = "Sort direction: asc or desc",
            example = "desc",
            defaultValue = "desc"
    )
    private String sortDirection;
}
